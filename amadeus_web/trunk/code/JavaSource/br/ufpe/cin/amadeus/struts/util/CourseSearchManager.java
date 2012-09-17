package br.ufpe.cin.amadeus.struts.util;

import java.util.ArrayList;
import java.util.List;

public class CourseSearchManager {

	private List<CourseSearchResult>[] csr;
	private List<CourseSearchResult> currentList;
	private int pageIndex;
	private int lastPageIndex;
	private List<Integer> pageIndexes;
	
	private final int RESULTS_PER_PAGE = 10;
	private final int QUICK_ACCESS_PAGES = 5;
	
	public final String FIRST = "first";
	public final String PREVIOUS = "previous";
	public final String NEXT = "next";
	public final String LAST = "last";
	
	private int firstCourse = 0;
	private int lastCourse = 0;
	
	private int noResults;
	private String criteria;
		
	public CourseSearchManager(List<CourseSearchResult>[] csr, String criteria) {
		this.csr = csr;
		this.pageIndex = 1;
		double factor = (double) (csr[0].size() + csr[1].size() + csr[2].size())/ RESULTS_PER_PAGE;
		this.noResults = csr[0].size() + csr[1].size() + csr[2].size();
		
		lastPageIndex = (int) Math.ceil(factor);
		
		this.currentList = new ArrayList<CourseSearchResult>();
		this.pageIndexes = new ArrayList<Integer>();
		this.updateCurrentList();
		this.updatePageIndexes();
		
		this.criteria = criteria;
	}
	
	public List<CourseSearchResult>[] getCsr() {
		return this.csr;
	}
	public int getFirstCourse() {
		return firstCourse;
	}
	public int getLastCourse() {
		return lastCourse;
	}
	public int getPageIndex() {
		return this.pageIndex;
	}
	public int getLastPageIndex() {
		return this.lastPageIndex;
	}
	public List<Integer> getPageIndexes() {
		return this.pageIndexes;
	}
	public List<CourseSearchResult> getCurrentList() {
		return this.currentList;
	}
	public String getCriteria() {
		return this.criteria;
	}
	public int getNoResults() {
		return this.noResults;
	}
	public boolean hasMoreThanOnePage() {
		return this.noResults > RESULTS_PER_PAGE;
	}

	public void update(String page) {
		if (page.equals(FIRST)) {
			pageIndex = 1;
		}
		else if (page.equals(PREVIOUS)) {
			pageIndex--;
		}
		else if (page.equals(NEXT)) {
			pageIndex++;
		}
		else if (page.equals(LAST)) {
			pageIndex = lastPageIndex;
		}
		else {
			pageIndex = Integer.parseInt(page);
		}
		this.updateCurrentList();
		this.updatePageIndexes();
	}
	
	private void updateCurrentList() {
		this.currentList.clear();
		this.firstCourse = (pageIndex - 1) * RESULTS_PER_PAGE;
		this.lastCourse = this.firstCourse + RESULTS_PER_PAGE;
		int totalSize = csr[0].size() + csr[1].size() + csr[2].size();

		if (this.lastCourse > totalSize)
			this.lastCourse = totalSize;
		
		int courseArrayIndex;
		int courseNumberInArray;
		
		for (int i = this.firstCourse; i < this.lastCourse; i++) {
			courseArrayIndex = findArrayIndex(i);
			courseNumberInArray = findCourseNumber(courseArrayIndex, i);
			currentList.add(csr[courseArrayIndex].get(courseNumberInArray));
		}
	}
	
	public int findCourseNumber(int courseArrayNumber, int course) {
		int courseInArray = 0;
		if (courseArrayNumber == 0) {
			courseInArray = course;  //course = number of the course in general listing
		} else if (courseArrayNumber == 1) {
			courseInArray = course - csr[0].size();
		} else {
			courseInArray = course - (csr[0].size() + csr[1].size());
		}
		return courseInArray;
	}
	
	public int findArrayIndex(int courseNumber) {
		int index = 0;
		if ( (courseNumber >= csr[0].size()) && (courseNumber < (csr[0].size() + csr[1].size()) ) ) {
			index = 1;
		} else if ( (courseNumber >= (csr[1].size() + csr[0].size())) && (courseNumber < (csr[0].size() + csr[1].size() + csr[2].size()))  ) {
			index = 2;
		}		
		return index;
	}
	
	private void updatePageIndexes() {
		this.pageIndexes.clear();
		
		int aux = (int) Math.floor(QUICK_ACCESS_PAGES/2);
		int firstValue = pageIndex - aux;
		int lastValue = pageIndex + aux;
		
		if (pageIndex == 1 || pageIndex == 2) {
			firstValue = 1;
			lastValue = QUICK_ACCESS_PAGES;
		} else if ((pageIndex == lastPageIndex) ||
				   (pageIndex == lastPageIndex - 1)) {
			firstValue = lastPageIndex - QUICK_ACCESS_PAGES + 1;
			lastValue = lastPageIndex;
		} 
		for (int index = firstValue; index <= lastValue; index++) {
			if (index > 0 && index <= lastPageIndex)
				pageIndexes.add(index);
		}
	}
}
