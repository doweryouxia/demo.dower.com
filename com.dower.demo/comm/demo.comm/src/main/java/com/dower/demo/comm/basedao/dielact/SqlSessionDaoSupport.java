package com.dower.demo.comm.basedao.dielact;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

public abstract class SqlSessionDaoSupport extends DaoSupport {
	private SqlSession sqlSession;

	private boolean externalSqlSession;

	@Autowired(required = false)
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		if (!this.externalSqlSession) {
			this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
		}
	}

	@Autowired(required = false)
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSession = sqlSessionTemplate;
		this.externalSqlSession = true;
	}

	/**
	 * Users should use this method to get a SqlSession to call its statement methods
	 * This is SqlSession is managed by spring. Users should not commit/rollback/close it
	 * because it will be automatically done.
	 * 
	 * @return Spring managed thread safe SqlSession 
	 */
	public final SqlSession getSqlSession() {
		return this.sqlSession;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void checkDaoConfig() {
		Assert.notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
	}

}