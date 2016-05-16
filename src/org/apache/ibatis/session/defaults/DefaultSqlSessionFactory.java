/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session.defaults;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

/**
 * 默认的SqlSessionFactory 创建sqlSession的工厂
 * @author Clinton Begin
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

  private final Configuration configuration;

  public DefaultSqlSessionFactory(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * 开一个sqlSession(根据配置文件中的DataSource),默认执行器,无隔离级别,不自动提交
   */
  public SqlSession openSession() {
    return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, false);
  }

  //openSession 重载方法
  public SqlSession openSession(boolean autoCommit) {
    return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, autoCommit);
  }

  public SqlSession openSession(ExecutorType execType) {
    return openSessionFromDataSource(execType, null, false);
  }

  public SqlSession openSession(TransactionIsolationLevel level) {
    return openSessionFromDataSource(configuration.getDefaultExecutorType(), level, false);
  }

  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return openSessionFromDataSource(execType, level, false);
  }

  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return openSessionFromDataSource(execType, null, autoCommit);
  }

  //根据Connection来创建SqlSession
  public SqlSession openSession(Connection connection) {
    return openSessionFromConnection(configuration.getDefaultExecutorType(), connection);
  }

  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return openSessionFromConnection(execType, connection);
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  //***************core入口***************
  private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
    Transaction tx = null;
    try {
      final Environment environment = configuration.getEnvironment();//获得Configuration中的Environment
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);//(根据之前transactionManager中type指定的别名,创建出了对应的事务工厂)
      tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);//创建事务 如果type="JDBC",则创建出一个JdbcTransaction
      /**
       * 根据transaction创建Executor
       */
      final Executor executor = configuration.newExecutor(tx, execType);//**********事务被组合到了执行器当中**********/
      return new DefaultSqlSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      closeTransaction(tx); // may have fetched a connection so lets call close()
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      ErrorContext.instance().reset();
    }
  }

  private SqlSession openSessionFromConnection(ExecutorType execType, Connection connection) {
    try {
      boolean autoCommit;
      try {
        autoCommit = connection.getAutoCommit();
      } catch (SQLException e) {
        // Failover to true, as most poor drivers
        // or databases won't support transactions
        autoCommit = true;
      }      
      final Environment environment = configuration.getEnvironment();
      final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
      final Transaction tx = transactionFactory.newTransaction(connection);
      final Executor executor = configuration.newExecutor(tx, execType);
      return new DefaultSqlSession(configuration, executor, autoCommit);
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
    } finally {
      ErrorContext.instance().reset();
    }
  }

  private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
    if (environment == null || environment.getTransactionFactory() == null) {
      return new ManagedTransactionFactory();
    }
    return environment.getTransactionFactory();
  }

  private void closeTransaction(Transaction tx) {
    if (tx != null) {
      try {
        tx.close();
      } catch (SQLException ignore) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

}
