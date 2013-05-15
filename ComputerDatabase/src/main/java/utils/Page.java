package utils;

import java.util.List;

import entites.Computer;

public class Page {
	
	private int numPage;
	private int totalResults;
	private int maxResults;
	private boolean lastPage;
	private boolean firstPage;
	private int displayFrom;
	private int displayTo;
	private List<Computer> computers;
	
	public Page (int numPage, int maxResults, int totalResults, List<Computer> computers) {
		this.totalResults = totalResults;
		this.maxResults = maxResults;
		
		this.numPage = numPage;
		
		computeIsLastPage();
		computeIsFirstPage();
		computeDisplayTo();
		computeDisplayFrom();
		
		this.computers = computers;
	}
	
	private void computeDisplayTo () {
		if ( ((numPage + 1 )* maxResults) > totalResults )
			this.displayTo = totalResults;
		else
			this.displayTo = (numPage + 1 )* maxResults;
	}
	
	private void  computeDisplayFrom () {
		if (totalResults == 0)
			this.displayFrom = 0;
		else
			this.displayFrom = numPage * maxResults + 1;
	}
	
	private void computeIsLastPage () {
		if (totalResults - (numPage + 1) * maxResults < 1)
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
