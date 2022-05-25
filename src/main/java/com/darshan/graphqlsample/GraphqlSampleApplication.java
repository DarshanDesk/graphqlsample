package com.darshan.graphqlsample;

import com.darshan.graphqlsample.repository.impl.InMemoryLogonMetricsRepoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphqlSampleApplication {

	public static void main(String[] args) {
		//InMemoryLogonMetricsRepoImpl impl = new InMemoryLogonMetricsRepoImpl("./csv/logon/Monthly.csv");
		SpringApplication.run(GraphqlSampleApplication.class, args);
	}

}
