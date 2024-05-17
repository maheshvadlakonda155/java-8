package com.streams;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import com.streams.employee.data.loadEmployeesData;
import com.streams.entity.Employee;

public class StreamsAllExamples {

	public static void main(String[] args) {

		List<String> listOf = loadEmployeesData.loadEmployees().stream().filter(i -> i.id == 133)
				.map(i -> i.getName() + "--" + i.getSalary()).collect(Collectors.toList());
		System.out.println(listOf);

		loadEmployeesData.loadEmployees().stream().limit(4).distinct()
				.map(i -> i.getName() + "--" + i.getId() + "--" + i.getAge()).collect(Collectors.toList())
				.forEach(i -> System.out.println(i));

		// max salary
		loadEmployeesData.loadEmployees().stream().collect(Collectors.maxBy(Comparator.comparing(i -> i.getSalary())))
				.map(i -> i.getName() + "--" + i.getSalary()).ifPresent(i -> System.out.println("max -" + i));

		// min salary
		loadEmployeesData.loadEmployees().stream().collect(Collectors.minBy(Comparator.comparing(i -> i.salary)))
				.map(i -> i.getName() + "--" + i.getSalary()).ifPresent(i -> System.out.println("min salary" + i));

		// Average of salary
		Double averageSalary = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.averagingDouble(i -> i.salary));
		System.out.println("averageSalary -- " + averageSalary);

		// sum of salary
		DoubleSummaryStatistics summ = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.summarizingDouble(i -> i.salary + 2));
		System.out.println("sum -" + summ.getSum());

		// how many males and female employees
		Map<String, Long> gender = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
		System.out.println(gender);

		// how many departments
		Map<String, Long> departmentList = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

		for (Entry<String, Long> mapl : departmentList.entrySet()) {
			System.out.println("Key: " + mapl.getKey() + ", Value: " + mapl.getValue());
		}

		// get only departmentNames
		loadEmployeesData.loadEmployees().stream().map(Employee::getDepartment).distinct()
				.forEach(i -> System.out.println(i));

		// get the age of employees without duplicate age
		loadEmployeesData.loadEmployees().stream().map(e -> e.age).distinct().forEach(e -> System.out.println(e));

		// What is the average age of male and female employees?
		Map<String, Double> averageofAge = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getAge)));
		System.out.println("averageofAge-- " + averageofAge);

		// What is the average age of male and female employees?
		Map<String, Double> averageOfSalary = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
		System.out.println("averageOfSalary - " + averageOfSalary);

		// print the name of all departments in the organization
		Map<String, Long> countDepartment = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

		System.out.println("countDepartment --" + countDepartment);

		// get the details of highest paid employee
		Optional<Employee> maxSalary = loadEmployeesData.loadEmployees().stream()
				.collect(Collectors.maxBy(Comparator.comparing(i -> i.getSalary())));
		if (maxSalary.isPresent()) {
			System.out.println(maxSalary.get().name);

			// get the name of all employees who joined after 2015
			loadEmployeesData.loadEmployees().stream().filter(i -> i.getYearOfJoining() > 2015).map(i -> i.getName())
					.forEach(i -> System.out.println("employees names who joined after 2015 -- " + i));

			// count the number of employees in each department
			Map<String, Long> departmentNumbers = loadEmployeesData.loadEmployees().stream()
					.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
			System.out.println(departmentNumbers);

			// what is the average salary of each department
			Map<String, Double> avgOfEachDepartment = loadEmployeesData.loadEmployees().stream().collect(
					Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
			System.out.println("avgOfEachDepartment -" + avgOfEachDepartment);

		}
	}
}
