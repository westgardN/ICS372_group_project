package edu.metrostate.ics372.thatgroup.clinicaltrial.catalog.dao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Transactional
public class ClinicDaoImpl implements ClinicDao {
	@Autowired
	private HibernateTemplate  hibernateTemplate;
}
