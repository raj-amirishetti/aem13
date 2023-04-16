package com.aem.aemfeb.core.utils;

import java.sql.Connection;
import javax.sql.DataSource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.commons.datasource.poolservice.DataSourcePool;

@Component(immediate = true, service = DatabaseConnectionHelper.class)
public class DatabaseConnectionHelper {

	public static final Logger lOG = LoggerFactory.getLogger(DatabaseConnectionHelper.class);

	@Reference
	private DataSourcePool dataSourceService;

	public Connection getDataBaseConnection(String dataSourceName) {
		Connection con = null;
		try {
			DataSource dataSource = (DataSource) dataSourceService.getDataSource(dataSourceName);
			con = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
