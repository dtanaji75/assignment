package cardsequence;

class PlayingCard
{
	public PlayingCard()
	{	
	}
	
	/**
	 * This function takes input of cards sequences as string
	 * @param cards
	 * @return Returns true if more than 2 cards sequences are given otherwise false
	 */
	
	public boolean checkNoOfCards(String cards)
	{
		
		try
		{
			return cards.split(",").length>2;
		}
		catch(Exception e)
		{
			System.out.println("Exception"+e);
			return false;
		}
	}
	/**
	 * checkSequence method check given sequence is valid or not
	 * @param cards
	 * @return Returns true if sequence is valid otherwise returns false
	 */
	public boolean checkSequence(String cards)
	{
		try
		{
			 if(!(this.checkValidCardSuitsAndNumber(cards.split(",")) && this.checkCardSuitSimilarity(cards.split(","))))
					 return false;
			 //seperated all cards using comma
			 
			 String[] sequences=cards.split(",");
			 
			 //spliting every cards using # after spiliting it returns array of two elements
			 //In array first element is suit and second element is card number
			 
			 
			 for(int i=1;i<sequences.length;i++)
			 {
				 
				 int currentNumber=this.getCardNumber(sequences[i].split("#")[1]);
				 int prevNumber=this.getCardNumber(sequences[i-1].split("#")[1]);
				 
				 if(prevNumber>currentNumber)
				 {
					 currentNumber=prevNumber;
					 currentNumber=this.getCardNumber(sequences[i].split("#")[1]);
				 }
				 
				 
				 /*
				  * checking for Q,K,A sequence
				  */
				 if(sequences[i].split("#")[1].equalsIgnoreCase("A") && sequences[i-1].split("#")[1].equalsIgnoreCase("K") && i==sequences.length-1)
						 return true;

				 //checking card difference 
				 if((currentNumber-prevNumber)!=1)
					 return false;
				 
			 }
			 return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception"+e);
			return false;
		}
	}
	/**
	 * checkValidCardSuits method takes spilited cards array as input 
	 * @param cards
	 * @return Returns true if all cards suits are S(Spades),H(Hearts),D(Diamond),C(Clubs)
	 * And it will check for valid numbers i.e. Card is between Ace to King 
	 * otherwise returns false;
	 */
	private boolean checkValidCardSuitsAndNumber(String[] cards)
	{
		try
		{
			boolean validSuit=true;
			boolean validNumber=true;
			for(String card:cards)
			{
				validSuit=card.split("#")[0].equalsIgnoreCase("S") || card.split("#")[0].equalsIgnoreCase("H") || card.split("#")[0].equalsIgnoreCase("D") || card.split("#")[0].equalsIgnoreCase("C");
				validNumber=this.getCardNumber(card.split("#")[1])>0 && this.getCardNumber(card.split("#")[1])<=13;
				
				if(!validSuit)
				{	
					throw new CardSuitException("Invalid Card Suit::"+card.split("#")[0]);
				}
				if(!validNumber)
				{
					throw new CardNumberException("Invalid Card Number::"+card.split("#")[1]);
				}
			}
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception"+e);
			return false;
		}
	}
	/**
	 * checkCardSuitSimilarity method takes spilited cards array as input 
	 * @param cards
	 * @return Returns true if all cards suits are same otherwise returns false;
	 */
	private boolean checkCardSuitSimilarity(String[] cards)
	{
		try
		{
			boolean validSuit=true;
			String prevSuit=cards[0].split("#")[0];
			for(String card:cards)
			{
				if(!prevSuit.equalsIgnoreCase(card.split("#")[0]))
				{
					validSuit=false;
					break;
				}
			}
			return validSuit;
		}
		catch(Exception e)
		{
			System.out.println("Exception"+e);
			return false;
		}
	}
	private int getCardNumber(String card)
	{
		try
		{
			int cardNo=0;
			switch(card)
			{
				case "A": cardNo= 1;break;
				case "2": cardNo= 2;break;
				case "3": cardNo= 3;break;
				case "4": cardNo= 4;break;
				case "5": cardNo= 5;break;
				case "6": cardNo= 6;break;
				case "7": cardNo= 7;break;
				case "8": cardNo= 8;break;
				case "9": cardNo= 9;break;
				case "10": cardNo= 10;break;
				case "J": cardNo= 11;break;
				case "Q": cardNo= 12;break;
				case "K": cardNo= 13;break;
			}
			return cardNo;
		}
		catch(Exception e)
		{
			System.out.println("Exception"+e);
			return 0;
		}
	}
}
