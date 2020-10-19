import java.util.Comparator;

/**
 * @author ayushparikh
 * Compares by pageRank of page
 */
public class RankComparator implements Comparator
{
	
	/*
	 * Compares by pageRank of page
	 */
	@Override
	public int compare(Object o1, Object o2) 
	{
		WebPage wb1 = (WebPage) o1;
		WebPage wb2 = (WebPage) o2;
		
		if(wb1.getRank() < wb2.getRank())
			return 1;
		else 
			return -1;
	}

}
