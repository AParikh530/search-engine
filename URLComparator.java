import java.util.Comparator;

/**
 * @author ayushparikh
 * Compares by url of page
 */
public class URLComparator implements Comparator
{

	/*
	 * Compares by url of page
	 */
	@Override
	public int compare(Object o1, Object o2) 
	{
		WebPage wb1 = (WebPage) o1;
		WebPage wb2 = (WebPage) o2;
		
		return(wb1.getURL().compareTo(wb2.getURL()));		
	}

}
