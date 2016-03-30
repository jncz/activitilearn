package com.spss.activititest;

import java.sql.SQLException;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws SQLException {
		App a = new App();
		ProcessEngine processEngine = a.getEngine();//ProcessEngines.getDefaultProcessEngine();
		
		RepositoryService repoService = processEngine.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		repoService.createDeployment().addClasspathResource("frp.bpmn").name("pli").deploy();
		runtimeService.startProcessInstanceByKey("financialReport");
		
		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().list();

		taskService.complete(tasks.get(0).getId());
		System.out.println(tasks.size());
		
		tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();

		System.out.println(tasks.size());
	}

	private ProcessEngine getEngine() {
		ProcessEngine processEngine = ProcessEngineConfiguration
		    .createStandaloneProcessEngineConfiguration()
		    .setDatabaseType("mysql")
		    .setJdbcDriver("com.mysql.jdbc.Driver")
		    .setJdbcPassword("Pass1234")
		    .setJdbcUsername("root")
		    .setJdbcUrl("jdbc:mysql://localhost/activiti?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true")
//		    .setDatabaseSchemaUpdate("drop-create")
		    .buildProcessEngine();
		return processEngine;
	}
}
