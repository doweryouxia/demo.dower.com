package com.dower.demo.comm.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.dower.demo.comm.basedao.dielact.DefaultDao;



/**
 * Service 基础类
 * @author Wticher
 *
 */
public class BaseService {

	@Autowired
	protected DefaultDao defaultDao;

	public DefaultDao getDefaultDao() {
		return defaultDao;
	}

	public void setDefaultDao(DefaultDao defaultDao) {
		this.defaultDao = defaultDao;
	}

}
