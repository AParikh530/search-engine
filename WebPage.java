import java.util.LinkedList;

public class WebPage 
{
	private String url;
	private int index;
	private int rank;
	private String links;
	private LinkedList<String> keywords = new LinkedList<String>();
	
	/**
	 * @param url url of the page
	 * @param index index of the page
	 * @param rank pageRank of the page
	 * @param links the links this page points to
	 * @param keywords keywords of the url of this page
	 */ 
	public WebPage(String url, int index, int rank, String links, LinkedList<String> keywords) 
	{
		this.url = url;
		this.index = index;
		this.rank = rank;
		this.links = links;
		this.keywords = keywords;
	}
	
	/*
	 * Organizes this page in a neat format
	 * 
	 */
	public String toString()
	{
		String output = String.format("%3d   | %-20s  | %4d     | %-20s  | %20s", index, url, rank, links, keywords);  
		return output; 
	}
	
	/**
	 * @return url of this page
	 */
	public String getURL()
	{
		return url;
	}
	
	/**
	 * @return index of this page
	 */
	public int getIndex()
	{
		return index;
	}
	
	/**
	 * @param index sets the index of this page to the one in parameter
	 */
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	/**
	 * @return rank of this page
	 */
	public int getRank()
	{
		return rank;
	}
	
	/**
	 * @param rank sets the rank of this page to the one in parameter. 
	 */
	public void setRank(int rank)
	{
		this.rank = rank;
	}
	
	/**
	 * @param links sets the links of this page to the one in parameter
	 */
	public void setLinks(String links)
	{
		this.links = links;
	}
	
	/**
	 * @return keywords of this page
	 */
	public LinkedList<String> getKeywords()
	{
		return keywords;
	}
}
