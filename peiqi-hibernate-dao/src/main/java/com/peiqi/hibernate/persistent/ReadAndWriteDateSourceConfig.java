package com.peiqi.hibernate.persistent;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "readandWriteentityManagerFactory", basePackages = {
		"com.peiqi.hibernate.dao" })
public class ReadAndWriteDateSourceConfig {

	@Bean("readAndWritedataSource")
	@ConfigurationProperties(prefix = "spring.datasource.readandwrite")
	@Primary
	public DataSource dataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(getFilter());
		druidDataSource.setProxyFilters(filters);
		return druidDataSource;
	}

	// 开放ddl操作权限
	public WallFilter getFilter() {
		WallConfig config = new WallConfig();
		config.setNoneBaseStatementAllow(true);
		WallFilter filter = new WallFilter();
		filter.setConfig(config);
		return filter;
	}

	@Primary
	@Bean("readandWriteentityManagerFactory")
	public LocalContainerEntityManagerFactoryBean readandWriteentityManagerFactory(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages("com.peiqi.entity") // 设置实体类所在位置
				.persistenceUnit("readAndwritePersistenceUnit").build();
	}

	@Primary
	@Bean(name = "readandWriteTransactionManager")
	public PlatformTransactionManager readTransactionManager(
			@Qualifier("readandWriteentityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
		return new JpaTransactionManager(factory.getObject());
	}

}
