import java.io.FileReader;

import com.opencsv.CSVReader;

public class TwitterSearch {
	
	public String top7tweets(int x) {
		String Anime= "Most Favorited Tweets\n";
		int counter = 0;
		int startread = x;
		
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("C:\\Users\\Ritchie Chan\\Desktop\\animeCrawler7000.csv"),',', '\'',1);
			String[] nextLine;
			while((nextLine=reader.readNext())!= null && counter<(6+x))
			{	
				if(startread<counter)
				{
				Anime += nextLine[0]+ ": ";
				Anime += nextLine[2]+ " ";
				Anime += "\nFavorited:" + nextLine[1];
				Anime += "\n\n";
				}
				counter++;
			}
		}
		catch (Exception e)
		{
			System.out.println("cannot read top7Tweets");
			e.printStackTrace();
		}
	return Anime;
	}
}
