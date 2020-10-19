import java.util.Comparator;

/**
 * @author ayushparikh
 * Compares by index of page
 */
public class IndexComparator implements Comparator
{
	/*
	 * Compares by index of page
	 */
	@Override
	public int compare(Object o1, Object o2) 
	{
		WebPage wb1 = (WebPage) o1;
		WebPage wb2 = (WebPage) o2;
		
		if(wb1.getIndex() > wb2.getIndex())
			return 1;
		else 
			return -1;
	}

}
