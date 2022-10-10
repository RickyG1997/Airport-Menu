/* Ricardo Garcia
 This program takes information from a flight txt file and creates a menu depending on the material provided
 Also included is a list of frequent flyer codes who if entered correctly recieve a discount
 */
 
import java.util.Scanner;
import java.io.File;

public class CL1_Garcia{
	public static void main(String []args){
		try{
			//creating file objects for both flights and frequent flyers
			Scanner in = new Scanner (System.in);
			File fileObj = new File ("Flight.txt");
			Scanner scr = new Scanner (fileObj);
			File Frequent = new File ("FrequentFlyer.txt");
			Scanner Freq = new Scanner(Frequent);
			
			System.out.println("WELCOME TO MEJIA AIRPORT");
			int Input = 1;
			double roundtrip = 0.00;
			double oneway = 0.00;
			double TotalPricePF = 0.00;
			double FinalPrice = 0.00;
			String Menu = "";
			
			while(Input == 1){
				
				//Main Menu
				
				System.out.println("Please select a choice below (1-5)");
				System.out.println("\t 1. Add Flight");
				System.out.println("\t 2. View Trip");
				System.out.println("\t 3. Manage Trip");
				System.out.println("\t 4. Checkout");
				System.out.println("\t 5. Exit Mejia Airlines");
				scr.nextLine();
				int uChoice = in.nextInt();
				switch(uChoice){
					//Flight Menu
					case 1:{
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						System.out.println("|         AVAILABLE FLIGHTS FOR EP         |");
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						System.out.println("|          CITY   ONE WAY    ROUND TRIP    |");
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						
						while(scr.hasNextLine()){
							String line = scr.nextLine();
							String[] details = line.split(" ");
							String Fn = details[0];
							double Ot = Double.parseDouble(details[1]);
							double Rt = Double.parseDouble(details[2]);
							System.out.print("|");
							System.out.format("%16s%9s%9s", Fn + " | ", "$" + Ot + " | ", "$" + Rt);
							System.out.println(" ");
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
						
						scr = new Scanner (fileObj);	
						in = new Scanner(System.in);
						System.out.println("Please select the city you wish to go (Please spell exactly as seen on the menu):");
						String UC = in.nextLine();
						String UCF = UC.toUpperCase();
						scr.nextLine();
						int i = 1;
						String CityFlight = "";
						
						while(scr.hasNextLine() && i == 1){
							String FC = scr.next();
							String FCF = FC.toUpperCase();
							if(UCF.equals(FCF) == true){
								i = 0;
								System.out.println("You have selected " + FC);
								oneway = scr.nextDouble();
								roundtrip = scr.nextDouble();
								CityFlight = FC;
								scr = new Scanner (fileObj);
							
								in = new Scanner(System.in);
								System.out.println("Which flight would you like?");
								System.out.println("1. One-way");
								System.out.println("2. Round-Trip");
								int OorRDec = in.nextInt();
								String TypeFlight = "";
								switch(OorRDec){
									case 1:{
										TotalPricePF = oneway;
										TypeFlight = "One way   ";
									}
									break;
									case 2:{
										TotalPricePF = roundtrip;
										TypeFlight = "Round Trip";
									}
									break;
								}
								in = new Scanner(System.in);
								System.out.println("How many seats would you like?");
								int numSeats = in.nextInt();
								TotalPricePF = TotalPricePF * numSeats;
						
								//String UserInfo = "EL PASO To \t" + CityFlight + "\t [Type]" + TypeFlight + "\t [TOTAL]" + String.format("%.2f", TotalPricePF) + "\t [SEATS]" + numSeats;
								String UserInfo = "EL PASO To \t" + String.format("%15s%15s%15s%15s", CityFlight, "\t [Type]" + TypeFlight, "\t [TOTAL]" + String.format("%.2f", TotalPricePF), "\t [SEATS]" + numSeats);
								Menu = Menu + "\n" + UserInfo;
								FinalPrice = FinalPrice + TotalPricePF;
							}
						}
					}
					break;
					
					case 2:{
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						System.out.println("                              TRIP INFORMATION                                ");
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						System.out.println(Menu);
					}
					break;
					
					case 3:{
						in = new Scanner(System.in);
						System.out.println("This Selection clears out all of your previous flights, would you like to continue? (Type YES or NO)");
						String USel3 = in.nextLine();
						USel3 = USel3.toUpperCase();
						if(USel3.equals("YES")){
							Menu = "";
							FinalPrice = 0.00;
							roundtrip = 0.00;
							oneway = 0.00;
							TotalPricePF = 0.00;
							System.out.println("All flights have been cleared");
						}else{
							System.out.println("Flights not cleared");
						}
					}
					break;
					
					case 4:{
						System.out.println("Final price is: $" + String.format("%.2f", FinalPrice));
						System.out.println("Are you a part of our Frequent Flyer Club?");
						in = new Scanner(System.in);
						String UserFreq = in.nextLine();
						UserFreq = UserFreq.toUpperCase();
						if(UserFreq.equals("YES")){
							in = new Scanner(System.in);
							System.out.println("Please enter you Frequent Flyer code(Please make sure to enter code such as (2DRT56) not (2drt56): ");
							String FreFly = in.nextLine();
							int r = 1;
							
							while(Freq.hasNextLine() && r == 1){
								String FreCod = Freq.next();
								if(FreFly.equals(FreCod)){
									r = 0;
									FinalPrice = FinalPrice - (FinalPrice * 0.2);
									System.out.println("20% Discount Applied");
								}
							}
						}else{
							System.out.println("Next step loading...");
						}
						
						System.out.println("Your final price is: $" + String.format("%.2f", FinalPrice));
						System.out.println("Please enter a 16 digit visa/master card to finalize the purchase");
						in = new Scanner(System.in);
						long CardU = in.nextLong();
						String CardUser = CardU + "";
						int CardVer = CardUser.length();
						if(CardVer == 16){
							System.out.println("Your purchase for: ");
							System.out.println(Menu);
							System.out.println("was successfull. You paid: $" + String.format("%.2f", FinalPrice) + " using the card: " + CardU);
							System.out.println("Have a nice Flight");
						}else{
							System.out.println("Card number Invalid, please try checkout again");
						}
					}
					break;
					
					case 5:{
						Input = 0;
						System.out.println("Thank you for choosing Mejia Airlines, we hope you have a nice flight");
						System.out.println("Stay Safe, Stay Hydrated, and Keep Coding!!!");
					}
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Check code, error detected" + e);
		}
	}
}
