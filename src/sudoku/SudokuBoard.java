package sudoku;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

public class SudokuBoard extends JFrame 
{
	JTextField[][] txtSudokuBoardTextBox=null;
	JPanel[] pnlBoardPanel=null;
	Border blackline=null;
	JPanel pnlMainPanel=null,pnlHeadPanel=null,pnlBottomPanel=null;
	JButton btnStartGame=null;
	JRadioButton radEasy,radMedium,radDifficult=null;
	JLabel lblDifficulty=null,lblInstruction=null;
	String difficulty="";
	
	int[][] sudokuMatrix;
	
	public SudokuBoard()
	{
		sudokuMatrix=new int[9][9];
		createGUI();
		
		this.setLayout(new BorderLayout());
		this.add(pnlMainPanel,BorderLayout.CENTER);
		this.add(pnlHeadPanel,BorderLayout.PAGE_START);
		this.add(pnlBottomPanel,BorderLayout.PAGE_END);
		this.setTitle("Sudoku Game");
		this.setSize(450,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	/**
	 * Generate sudoku games ui
	 */
	private void createGUI()
	{
		try
		{
			//Head Panel
			pnlHeadPanel=new JPanel();
			pnlHeadPanel.setLayout(new FlowLayout());
			
			lblDifficulty=new JLabel("Choose Difficulty:");
			
			radEasy=new JRadioButton("Easy");
			radMedium=new JRadioButton("Medium");
			radDifficult=new JRadioButton("Difficult");
			
			radEasy.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					difficulty="Easy";
					
				}
			});
			radMedium.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					difficulty="Medium";
					
				}
			});
			radDifficult.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					difficulty="Difficult";
					
				}
			});
			
			ButtonGroup btnRadGrp=new ButtonGroup();
			btnRadGrp.add(radEasy);
			btnRadGrp.add(radMedium);
			btnRadGrp.add(radDifficult);
			
			btnStartGame=new JButton("Start Game");
			btnStartGame.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					generateNewSudoku();
					enableDisable(true);
					
				}
			});
			
			pnlHeadPanel.add(lblDifficulty);
			pnlHeadPanel.add(radEasy);
			pnlHeadPanel.add(radMedium);
			pnlHeadPanel.add(radDifficult);
			pnlHeadPanel.add(btnStartGame);
			
			
			txtSudokuBoardTextBox=new JTextField[9][9];
			blackline=BorderFactory.createLineBorder(Color.black);
			pnlBoardPanel=new JPanel[9];
			
			//Main panel code
			pnlMainPanel=new JPanel();
			pnlMainPanel.setLayout(new GridLayout(3,3,4,4));
			
			for(int i=0;i<9;i++)
			{
				pnlBoardPanel[i]=new JPanel();
				for(int j=0;j<9;j++)
				{
					Font textFont=new Font("Times New Romen", Font.PLAIN, 15);
					this.txtSudokuBoardTextBox[i][j]=new JTextField();
					this.txtSudokuBoardTextBox[i][j].setFont(textFont);
					this.txtSudokuBoardTextBox[i][j].setForeground(Color.black);
					this.txtSudokuBoardTextBox[i][j].setDisabledTextColor(Color.black);
					this.txtSudokuBoardTextBox[i][j].setBorder(blackline);
					this.txtSudokuBoardTextBox[i][j].setHorizontalAlignment(JTextField.CENTER);
					this.txtSudokuBoardTextBox[i][j].setName(i+"_"+j); // set textbox name as rowno_columnno format
					/**
					 * Event listener for textbox it occures when users enter keys
					 */
					this.txtSudokuBoardTextBox[i][j].addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							JTextField textbox=(JTextField)e.getSource();
							String[] indexs=textbox.getName().split("_");//seperates row and column
							System.out.println(textbox.getName());
							int row=Integer.parseInt(indexs[0]);
							int col=Integer.parseInt(indexs[1]);
							sudokuMatrix[row][col]=Integer.parseInt(textbox.getText());
							textbox.setText("");
							if(sudokuMatrix[row][col]>9 && sudokuMatrix[row][col]>9)
							{
								JOptionPane.showMessageDialog(null, "Enter number between 1 to 9");
								sudokuMatrix[row][col]=0;
								return;
							}
							
							boolean flag=checkValidity(sudokuMatrix,row,col,sudokuMatrix[row][col]);
							if(flag)
							{
								textbox.setText(""+sudokuMatrix[row][col]);
								if(checkWinner())
								{
									enableDisable(false);
								}
							}
							else
							{
								sudokuMatrix[row][col]=0;
								JOptionPane.showMessageDialog(null, "Wrong number entered. Please enter different number");
							}
						}
					});
//					this.txtSudokuBoardTextBox[i][j].setText("0");
				}
				this.pnlBoardPanel[i].setName("panel"+(i+1));
				this.pnlBoardPanel[i].setLayout(new GridLayout(3,3));
				
				this.pnlBoardPanel[i].setBorder(blackline);
				
				this.pnlMainPanel.add(pnlBoardPanel[i]);
			}
			
			
			this.addTextToPanel();
			
			//Bottom Panel
			this.pnlBottomPanel=new JPanel();
			this.pnlBottomPanel.setLayout(new FlowLayout());
			
			
			lblInstruction=new JLabel("Enter number between 1 to 9 in empty textbox and press enter.");
//			btnValidate=new JButton("Check Result");
			
			this.pnlBottomPanel.add(lblInstruction);
			
			this.enableDisable(false);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Problem in creating board::"+e.getMessage());
		}
	}
	/**
	 * enableDisable function changes enable property according to given flag
	 * @param flag
	 */
	private void enableDisable(boolean flag)
	{
		try
		{
			this.radEasy.setEnabled(!flag);
			this.radMedium.setEnabled(!flag);
			this.radDifficult.setEnabled(!flag);
			this.btnStartGame.setEnabled(!flag);
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					this.txtSudokuBoardTextBox[i][j].setEnabled(flag);
				}
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Proble in enabledisable ::"+e.getMessage());
		}
	}
	/*
	 * add textbox to panel
	 */
	private void addTextToPanel()
	{
		try
		{
			//Adds textbox to panel 1
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					this.pnlBoardPanel[0].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 2
			for(int i=0;i<3;i++)
			{
				for(int j=3;j<6;j++)
				{
					this.pnlBoardPanel[1].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 3
			for(int i=0;i<3;i++)
			{
				for(int j=6;j<9;j++)
				{
						this.pnlBoardPanel[2].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 4
			for(int i=3;i<6;i++)
			{
				for(int j=0;j<3;j++)
				{
					this.pnlBoardPanel[3].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 5
			for(int i=3;i<6;i++)
			{
				for(int j=3;j<6;j++)
				{
					this.pnlBoardPanel[4].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 6
			for(int i=3;i<6;i++)
			{
				for(int j=6;j<9;j++)
				{
					this.pnlBoardPanel[5].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 7
			for(int i=6;i<9;i++)
			{
				for(int j=0;j<3;j++)
				{
					this.pnlBoardPanel[6].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 8
			for(int i=6;i<9;i++)
			{
				for(int j=3;j<6;j++)
				{
					this.pnlBoardPanel[7].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
			//Adds textbox to panel 9
			for(int i=6;i<9;i++)
			{
				for(int j=6;j<9;j++)
				{
					this.pnlBoardPanel[8].add(this.txtSudokuBoardTextBox[i][j]);
				}
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Problem in add TextBox to panel::"+e.getMessage());
		}
	}
	/**
	 * 
	 * Generates random numbers from 1 to 9 set to given row and column
	 * rows and columns selected randomly every time
	 * It checks which radio button is user selected
	 * if easy selected it generates 30 digits 
	 * if medium selected it generates 22 digits
	 * if difficult selected it generates 17 digits
	 */
	private void generateNewSudoku()
	{
		try
		{
			Random sudoRandom=new Random();
			int limit=0;
			if(radEasy.isSelected())
				limit=30;
			if(radMedium.isSelected())
				limit=22;
			if(radDifficult.isSelected())
				limit=17;
			
			for(int i=1;i<=limit;)
			{
				int row=(sudoRandom.nextInt(9));
				int col=(sudoRandom.nextInt(9));
				
						
				String data=txtSudokuBoardTextBox[row][col].getText();
				
				if(data.equals(""))
				{
					while(true)
					{
						sudokuMatrix[row][col]=(sudoRandom.nextInt(9))+1;
//						System.out.println("Random Number::"+sudokuMatrix[i][j]);
						boolean flag=validateSudoku(row, col);
//						System.out.println("result::"+flag);
						if(flag)
							break;
					}
					this.txtSudokuBoardTextBox[row][col].setText(""+sudokuMatrix[row][col]);
					this.txtSudokuBoardTextBox[row][col].setEnabled(false);
					this.txtSudokuBoardTextBox[row][col].setBackground(Color.LIGHT_GRAY);
					this.txtSudokuBoardTextBox[row][col].setForeground(Color.BLACK);
					i++;
				}
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Problem in generating new sudoku::"+e.getMessage());
		}
	}
	/**
	 * this function validates sudokutable
	 * @param row
	 * @param col
	 * @return true/false
	 */
	private boolean validateSudoku(int row,int col)
	{
		try
		{
			boolean boxFlag=this.boxValidate(row, col, sudokuMatrix[row][col]);
			boolean rowFlag=this.rowValidate(row, col, sudokuMatrix[row][col]);
			boolean colFlag=this.colValidate(row, col,sudokuMatrix[row][col]);
			
			System.out.println("Box flag::"+boxFlag);
			System.out.println("row Flag::"+rowFlag);
			System.out.println("Col Flag::"+colFlag);
			
			return boxFlag && rowFlag && colFlag;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	/**
	 * Box validation of sudoku done inthis function
	 * @param row
	 * @param col
	 * @param value
	 * @return true/false
	 */ 
	private boolean boxValidate(int row,int col,int value)
	{
		try
		{
			
			String panelValue=this.txtSudokuBoardTextBox[row][col].getParent().getName();
			System.out.println(panelValue);
			
			if(panelValue.equals("panel1"))
			{
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
					}
				}
			}
			if(panelValue.equals("panel2"))
			{
				for(int i=0;i<3;i++)
				{
					for(int j=3;j<6;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
							
					}
				}
			}
			if(panelValue.equals("panel3"))
			{
				for(int i=0;i<3;i++)
				{
					for(int j=6;j<9;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
							
					}
				}
			}
			if(panelValue.equals("panel4"))
			{
				for(int i=3;i<6;i++)
				{
					for(int j=0;j<3;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
					}
				}
			}
			if(panelValue.equals("panel5"))
			{
				for(int i=3;i<6;i++)
				{
					for(int j=3;j<6;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
					}
				}
			}
			if(panelValue.equals("panel6"))
			{
				for(int i=3;i<6;i++)
				{
					for(int j=6;j<9;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
					}
				}
			}
			if(panelValue.equals("panel7"))
			{
				for(int i=6;i<9;i++)
				{
					for(int j=0;j<3;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
					}
				}
			}
			if(panelValue.equals("panel8"))
			{
				for(int i=6;i<9;i++)
				{
					for(int j=3;j<6;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
					}
				}
			}
			if(panelValue.equals("panel9"))
			{
				for(int i=6;i<9;i++)
				{
					for(int j=6;j<9;j++)
					{
						int currentValue=0;
						if(!txtSudokuBoardTextBox[i][j].getText().equals(""))
							currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][j].getText());
						if(currentValue==value)
							return false;
					}
				}
			}
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Exception in boxValidate::"+e.getMessage());
			return false;
		}	
	}
	/**
	 * Row validation of sudoku done in this function
	 * @param row
	 * @param col
	 * @param value
	 * @return true/false
	 */
	private boolean rowValidate(int row,int col,int value)
	{
		try
		{
			for(int i=0;i<9;i++)
			{
				if(i==col)
					continue;
				int currentValue=0;
				if(!txtSudokuBoardTextBox[row][i].getText().equals(""))
					currentValue=Integer.parseInt((txtSudokuBoardTextBox[row][i].getText()));
				if(currentValue==value)
					return false;
			}
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Exception in boxValidate::"+e.getMessage());
			return false;
		}
	}
	
	/**
	 * Column validation of sudoku don in this function
	 * @param row
	 * @param col
	 * @param value
	 * @return true/false
	 */
	private boolean colValidate(int row,int col,int value)
	{
		try
		{
			
			for(int i=0;i<9;i++)
			{
				if(i==row)
					continue;
				int currentValue=0;
				if(!txtSudokuBoardTextBox[i][col].getText().equals(""))
					currentValue=Integer.parseInt(txtSudokuBoardTextBox[i][col].getText());
				if(currentValue==value)
					return false;
			}
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Exception in boxValidate::"+e.getMessage());
			return false;
		}
	}
	/**
	 * this check sudoku is valid or not after entering number by user
	 * @param sudoku
	 * @param row
	 * @param column
	 * @param digit
	 * @return true/false
	 */
	public boolean checkValidity(int sudoku[][],int row,int column,int digit)
	{
		try
		{
			return this.validateSudoku(row, column);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Exception in boxValidate::"+e.getMessage());
			return false;
		}
	}
	/**
	 * This checks that user has completed sudoku or not
	 * @return true/false
	 */
	public boolean checkWinner()
	{
		try
		{
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					if(sudokuMatrix[i][j]==0)
						return false;
				}
			}
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "Exception in boxValidate::"+e.getMessage());
			return false;
		}
	}
}
