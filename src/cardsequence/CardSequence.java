package cardsequence;

import java.util.Scanner;
public class CardSequence 
{
	private static Scanner scanner;
	private static PlayingCard cardDeck;
	public static void main(String[] args)
	{
		try
		{
			scanner = new Scanner(System.in);
			cardDeck = new PlayingCard();
			
			System.out.print("Enter cards:");
			String cards=scanner.next();
			
			//Checking card sequence is valid.
			if(!cardDeck.checkNoOfCards(cards))
			{
				System.out.println("Please provid more than 2 cards");
				
			}
			else if(cardDeck.checkSequence(cards))
			{
				System.out.println("Valid Sequence");
			}
			else
			{
				System.out.println("Not valid Sequence");
			}
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
