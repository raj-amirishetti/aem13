package com.aem.aemfeb.core.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.aemfeb.core.bean.Employees;
import com.aem.aemfeb.core.dbutils.DatabaseConnectionHelper;
import com.aem.aemfeb.core.service.EmployeeI;

@Component(immediate = true, service = EmployeeI.class)
public class EmployeeImpl implements EmployeeI {

	@Reference
	DatabaseConnectionHelper helper;

	/*
	 * @Reference Employees e;
	 */

	Connection con = null;
	PreparedStatement pstmt = null;

	public static final Logger log = LoggerFactory.getLogger(EmployeeImpl.class);
	


	@Override
	public boolean registerEmployeeDetails(String name, String age, String email, String weight) {

		log.info("inside registerEmployeesDetails method");
		try {

			boolean flag = false;

			con = helper.getDataBaseConnection("test");

			pstmt = con.prepareStatement("insert into employee values(?, ?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, age);
			pstmt.setString(3, email);
			pstmt.setString(4, weight);

			int i = pstmt.executeUpdate();
			if (i == 1) {

				flag = true;
				log.info("query success");

			} else {

				log.info("query fail");
				flag = false;

			}
		} catch (Exception e) {

		} finally {
			if (con != null) {

				try {
					pstmt.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}

		return true;
	}

	@Override
	public boolean deleteEmployee(String name) {

		log.info("inside deleteEmployee");
		int status = 0;
		try {
			boolean flag = false;

			con = helper.getDataBaseConnection("test");

			pstmt = con.prepareStatement("DELETE FROM employee WHERE name=?");
			pstmt.setString(1, name);
			status = pstmt.executeUpdate();

			if (status == 1) {

				flag = true;
				log.info("query success");

			} else {

				log.info("query fail");
				flag = false;

			}

		} catch (Exception e) {

		} finally {
			if (con != null) {

				try {
					pstmt.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}

		return true;
	}

	@Override
	public List<Employees> getAllEmployees() {

		log.info("inside getAllEmployees method");
		List<Employees> list = new ArrayList<Employees>();
		con = helper.getDataBaseConnection("test");

		try {
			pstmt = con.prepareStatement("select * from employee");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Employees e = new Employees();
				e.setName(rs.getString(1));
				e.setAge(rs.getString(2));
				e.setEmail(rs.getString(3));
				e.setWeight(rs.getString(4));
				list.add(e);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Employees getEmployeeByName(String name) {

		log.info("getemployeebyName " + name);

		Employees e = new Employees();

		try {
			con = helper.getDataBaseConnection("test");
			pstmt = con.prepareStatement("select * from employee where name=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				e.setName(rs.getString(1));
				e.setAge(rs.getString(2));
				e.setEmail(rs.getString(3));
				e.setWeight(rs.getString(4));
			}
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return e;

	}

	@Override
	public int updateEmployee(Employees e) {
		int status = 0;

		try {
			con = helper.getDataBaseConnection("test");
			pstmt = con.prepareStatement("update employee set name=?,age=?,email=?,weight=? where name=?");
			pstmt.setString(1, e.getName());
			pstmt.setString(2, e.getAge());
			pstmt.setString(3, e.getEmail());
			pstmt.setString(4, e.getWeight());
			pstmt.setString(5, e.getName());

			status = pstmt.executeUpdate();
			log.info("status " + status);

			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return status;

	}

}
