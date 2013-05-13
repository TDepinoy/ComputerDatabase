package utils;

import java.util.List;

import controller.ShowComputerListServlet;

import service.GestionComputerService;

import entites.Computer;

public class Page {
	
	private int numPage;
	private int totalResults;
	private boolean lastPage;
	private boolean firstPage;
	private int displayFrom;
	private int displayTo;
	private List<Computer> computers;
	
	public Page (int numPage, OptionsRequest sortOptions) {
		this.totalResults = GestionComputerService.getInstance().countComputers(sortOptions.getNameFilter());	
		
		if (numPage < 0 || (numPage > 0 && (totalResults - numPage * ShowComputerListServlet.MAX_RESULTS_PER_PAGE < 0) ))
			this.numPage = 0;
		else
			this.numPage = numPage;
		
		computeIsLastPage();
		computeIsFirstPage();
		computeDisplayTo();
		computeDisplayFrom();
		
		this.computers = GestionComputerService.getInstance().getComputers(displayFrom, ShowComputerListServlet.MAX_RESULTS_PER_PAGE, sortOptions);
	}
	
	private void computeDisplayTo () {
		if ( ((numPage + 1 )* ShowComputerListServlet.MAX_RESULTS_PER_PAGE) > totalResults )
			this.displayTo = totalResults;
		else
			this.displayTo = (numPage + 1 )* ShowComputerListServlet.MAX_RESULTS_PER_PAGE;
	}
	
	private void  computeDisplayFrom () {
		if (totalResults == 0)
			this.displayFrom = 0;
		else
			this.displayFrom = numPage * ShowComputerListServlet.MAX_RESULTS_PER_PAGE + 1;
	}
	
	private void computeIsLastPage () {
		if (totalResults - (numPage + 1) * ShowComputerListServlet.MAX_RESULTS_PER_PAGE < 1)
			lastPage = true;
		else
			lastPage = false;
	}
	
	private void computeIsFirstPage () {
		if (numPage == 0)
			firstPage = true;
		else
			firstPage = false;
	}

	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public int getDisplayFrom() {
		return displayFrom;
	}

	public void setDisplayFrom(int displayFrom) {
		this.displayFrom = displayFrom;
	}

	public int getDisplayTo() {
		return displayTo;
	}

	public void setDisplayTo(int displayTo) {
		this.displayTo = displayTo;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}

	@Override
	public String toString() {
		return "Page [numPage=" + numPage + ", totalResults=" + totalResults
				+ ", lastPage=" + lastPage + ", firstPage=" + firstPage
				+ ", displayFrom=" + displayFrom + ", displayTo=" + displayTo
				+ ", computers=" + computers + "]";
	}
}
