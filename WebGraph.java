import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author ayushparikh
 *
 */
public class WebGraph 
{
	public static final int MAX_PAGES = 40;
	private static LinkedList<WebPage> pages = new LinkedList<WebPage>();
	private static int[][] edges = new int[MAX_PAGES][MAX_PAGES]; 
	private static int indexCounter = 0;
	private static int boundCounter = 0;
	private static WebPage webpage;
	
	
	/**
	 * @param pagesFile File with urlName and their keywords
	 * @param linksFile file with links between URLs
	 * @throws IllegalArgumentException thrown if files not found 
	 */
	public static void buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException
	{
		BufferedReader reader;
		String pageLine;
		try
		{
			reader = new BufferedReader(new FileReader(pagesFile)); 
			while((pageLine = reader.readLine()) != null)
			{
				while(pageLine.charAt(0) == ' ')
				{
					pageLine = pageLine.substring(1, pageLine.length());
				}
				String[] words = pageLine.split(" ");
				String url = words[0];
				LinkedList<String> keywords = new LinkedList<String>();
				for(int i = 1; i < words.length; i++)
				{
					keywords.add(words[i]);
				}
				webpage = new WebPage(url, indexCounter, 0, "$$$", keywords);
				indexCounter++;
				pages.add(webpage);
				boundCounter++;
			}
			
			reader = new BufferedReader(new FileReader(linksFile));
			while((pageLine = reader.readLine()) != null)
			{
				while(pageLine.charAt(0) == ' ')
				{
					pageLine = pageLine.substring(1, pageLine.length());
				}
				String[] webs = pageLine.split(" ");
				String web1 = webs[0];
				String web2 = webs[1];
				int pos1 = getIndexOfPage(web1);
				int pos2 = getIndexOfPage(web2);
				edges[pos1][pos2] = 1;
				updatePageRanks();

			}
		}
		catch(Exception e)
		{
			System.out.println("MISTAKE");
		}		
	}
	
	/**
	 * @param webpageName takes in a string for the name of the page 
	 * @return returns the index of the page
	 */
	public static int getIndexOfPage(String webpageName)
	{
		for(int i = 0; i < pages.size(); i++)
		{
			if(pages.get(i).getURL().equals(webpageName))
				return i;
		}
		return -1;
	}
	
	/**
	 * @param url the user inputed url as a string
	 * @param keywords keywords of that url
	 * @throws IllegalArgumentException thrown if url already exists or is null
	 */
	public void addPage(String url, LinkedList<String> keywords) throws IllegalArgumentException
	{
		if(url == null || keywords == null)
			throw new IllegalArgumentException();
		for(int i = 0; i < pages.size(); i++)
		{
			if(pages.get(i).getURL().equals(url))
				throw new IllegalArgumentException();
		}
		webpage = new WebPage(url, indexCounter, 0, "$$$", keywords);
		indexCounter++;
		pages.add(webpage);
		boundCounter++;
		updatePageRanks();
	}
	
	/**
	 * @param source where the hyperlink to the dest url will start from
	 * @param destination the url where the hyperlink will connect to
	 * @throws IllegalArgumentException thrown if url is null or could not be found
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException
	{
		if(source == null || destination == null)
			throw new IllegalArgumentException();
		int urlCounter = 0;
		for(int i = 0; i < pages.size(); i++)
		{
			if(pages.get(i).getURL().equals(source) || pages.get(i).getURL().equals(destination))
				urlCounter++;
		}
		if(urlCounter < 2)
			throw new IllegalArgumentException();
		int pos1 = getIndexOfPage(source);
		int pos2 = getIndexOfPage(destination);
		edges[pos1][pos2] = 1; 
		updatePageRanks();
	}
	
	/**
	 * @param url URL of the page to remove from the graph
	 */
	public void removePage(String url)
	{
		boolean foundURL = false;
		int indexOfRemovedURL = 0;
		for(int i = 0; i < pages.size(); i++)
		{
			if(pages.get(i).getURL().equals(url))
			{
				foundURL = true;
				indexOfRemovedURL = i;
				pages.remove(i);
			}
			//Decreasing indexes after the index of the removed page by one
			if(foundURL && i != pages.size())
			{
				pages.get(i).setIndex(pages.get(i).getIndex()-1); 
			}
		}
		
		if(url != null && foundURL)
		{
			//Shifts all elements horizontally
			for(int i = 0; i < pages.size(); i++)
			{
				for(int j = indexOfRemovedURL; j < pages.size(); j++) 
				{
					edges[i][j] = edges[i][j+1];
				}
			}
			//Shifts all elements vertically
			for(int i = indexOfRemovedURL; i < pages.size(); i++)
			{
				for(int j = 0; j < pages.size(); j++)
				{
					edges[i][j] = edges[i+1][j];
				}
			}
			updatePageRanks();
		}
	} 
	
	
	/**
	 * @param source URL of the WebPage to remove the link
	 * @param destination URL of the link to be removed
	 */
	public void removeLink(String source, String destination)
	{
		int urlCounter = 0;
		int sourceIndex = 0;
		int destIndex = 0;
		for(int i = 0; i < pages.size(); i++)
		{
			if(pages.get(i).getURL().equals(source) || pages.get(i).getURL().equals(destination))
			{
				urlCounter++;
				if(pages.get(i).getURL().equals(source))
					sourceIndex = i;
				else
					destIndex = i;
			}	
		}		
		if(urlCounter == 2)
		{
			edges[sourceIndex][destIndex] = 0; 
			updatePageRanks();
		}
	}
	
	/**
	 * Calculates and assigns page rank for every page
	 */
	public static void updatePageRanks()
	{
		for(int i = 0; i < pages.size(); i++)
		{
			int a = 0;
			for(int j = 0; j < pages.size(); j++)
			{
				if(edges[j][i] == 1)
					a++;				
			}
			pages.get(i).setRank(a); 
		}
	}
	
	/**
	 * @param a determines which comparison method to use
	 */
	public void printTable(int a)
	{	
		for(int i = 0; i < pages.size(); i++)
		{
			String replaceWith = determineLinks(pages.get(i));
			pages.get(i).setLinks(replaceWith); 
		}
		
		if(a == 1)
		{
			IndexComparator indexCompare = new IndexComparator();
			Collections.sort(pages, indexCompare);
		}
		else if(a == 2)
		{
			URLComparator urlCompare = new URLComparator();
			Collections.sort(pages, urlCompare);
			
		}
		else 
		{
			RankComparator rankCompare = new RankComparator();
			Collections.sort(pages, rankCompare); 
		}
		System.out.printf("%s | %-20s  | %2s | %13s         | %17s", "Index", "URL", "PageRank", "Links", "Keywords");
		System.out.println("\n--------------------------------------------------------------"
				+ "--------------------------------------------------");
		for(WebPage wb: pages)
		{
			System.out.println(wb.toString());
		}
	}
	
	/**
	 * @param wb Determines the links the url from wb points to
	 * @return the links the url from wb points to
	 */
	public String determineLinks(WebPage wb)
	{
		String output = "";
		for(int i = 0; i < pages.size(); i++)
		{
			if(pages.get(i).getURL().equals(wb.getURL()))
			{
				for(int j = 0; j < pages.size(); j++)
				{
					if(edges[i][j] == 1)
						output+= j + ", ";
				}
			}
		}
		
		return output;
	}
	
	/**
	 * @param keyword finds which urls contain keyword
	 * @throws IllegalArgumentException thrown if url does not exist
	 */
	public void printBasedOnKeyword(String keyword) throws IllegalArgumentException
	{
		RankComparator rankCompare = new RankComparator();
		LinkedList<WebPage> urlWithSameKeyword = new LinkedList<WebPage>();
		int keywordCounter = 0;
		//Adding urls with the same keyword to the new linkedList
		for(int i = 0; i < pages.size(); i++)
		{
			for(int j = 0; j < pages.get(i).getKeywords().size(); j++)
			{
				if(pages.get(i).getKeywords().get(j).equals(keyword))
				{
					urlWithSameKeyword.add(pages.get(i));
					keywordCounter++;
				}
			}
		}
		
		if(keywordCounter < 1)
			throw new IllegalArgumentException();
		
		//Sorting the new linkedList based on pageRank
		Collections.sort(urlWithSameKeyword, rankCompare); 
			
		System.out.printf("%s | %s | %s", "Rank", "PageRank", "URL");
		System.out.println("\n--------------------------------------");
			
		//Print a table containing those urls added 
		int rank = 0;
		for(int i = 0; i < urlWithSameKeyword.size(); i++)
		{
			rank++;
			String table = String.format("%2d   |  %3d     |  %2s", rank, urlWithSameKeyword.get(i).getRank(), urlWithSameKeyword.get(i).getURL());
			System.out.println(table);
		}		
	}
}
