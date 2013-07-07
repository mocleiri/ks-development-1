package org.kuali.student.deploy.spring;

import java.util.Arrays;
import java.util.List;

import org.kuali.common.jdbc.JdbcProjectContext;
import org.kuali.common.util.MetaInfSqlProjectContext;
import org.kuali.common.util.ProjectContext;
import org.kuali.common.util.property.ProjectProperties;
import org.kuali.common.util.spring.ConfigUtils;
import org.kuali.common.util.spring.MavenPropertySourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DumpSourceDbMavenPropertySourceConfig extends MavenPropertySourceConfig {

	@Override
	protected List<ProjectProperties> getOtherProjectProperties() {
		ProjectContext jdbc = new JdbcProjectContext();
		ProjectContext sql = new MetaInfSqlProjectContext();
		ProjectContext dump = new DumpSourceDbProjectContext();
		return ConfigUtils.getProjectProperties(Arrays.asList(jdbc, sql, dump));
	}

}
