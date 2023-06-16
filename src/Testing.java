import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.sql.SQLException;

/*
 * In JUnit 5,
 * We can use our own MethodOrderer to execute the functions in order.
 * We can use the @Order annotation to enforce tests to run in a specific order.
 * Then the functions are executed in order of number for each function (Order(The Number)).
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class Testing {

	/*
	 * static CommandProcessor commandProcessor = new CommandProcessor();
	 * We avoid using this because we need to throw an exception and 
	 * using static in the same time and by this way we can not throws
	 * that exception.
	 */
	static CommandProcessor commandProcessor;		// static here to be for all methods.

	/*
	 * The static block is used for initializing
	 * the commandProcessor object.
	 */
    static
    {
    	try {
			commandProcessor = new CommandProcessor();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
	@Order(1)
    public void dumpTest1() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest1");
		assertEquals(7,commandProcessor.processor("dump"));
	}
	
	@Test
	@Order(2)
	public void insertTest1() throws SQLException {
		/*Inserted Rectangles
		 * Insert only name that begins with Letters ,
		 * Positive X ,Positive Y ,Positive W , H=Positive ,
		 * X+W<=1024 && Y+H<=1024
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Inserted Rectangles~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest1");
		assertEquals(1,commandProcessor.processor("insert r1 100 100 50 50"));
		assertEquals(1,commandProcessor.processor("insert r2 900 800 124 224"));
		assertEquals(1,commandProcessor.processor("insert R2 900 800 124 224"));
		assertEquals(1,commandProcessor.processor("insert r4 22 22 6 6"));
		assertEquals(1,commandProcessor.processor("insert Rec 100 200 200 800"));
		assertEquals(1,commandProcessor.processor("insert rec 850 30 60 500"));
		assertEquals(1,commandProcessor.processor("insert largeRec 0 0 1000 1"));
		assertEquals(1,commandProcessor.processor("insert exist_1 11 11 20 20"));
		assertEquals(1,commandProcessor.processor("insert smallRec 15 30 15 30"));
		assertEquals(1,commandProcessor.processor("insert smallRec 5 5 8 8"));
		assertEquals(1,commandProcessor.processor("insert r50 68 12 60 14"));
		assertEquals(1,commandProcessor.processor("insert exist_2 30 15 30 15"));
		assertEquals(1,commandProcessor.processor("insert LRec 1000 1000 20 20"));
		assertEquals(1,commandProcessor.processor("insert LRec 1000 1000 20 20"));
		assertEquals(1,commandProcessor.processor("insert MRec 150 90 100 80"));
		assertEquals(1,commandProcessor.processor("insert SRec 50 30 40 20"));
		
	}
	
	@Test
	@Order(3)
	public void searchTest1() throws SQLException {
		
	    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~SearchTest1");
	    
		assertEquals(5,commandProcessor.processor("search Rec"));
		assertEquals(5,commandProcessor.processor("search rec"));
		assertEquals(5,commandProcessor.processor("search Recc"));
		assertEquals(5,commandProcessor.processor("search exist_2"));
		assertEquals(5,commandProcessor.processor("search r2"));
	}
	
	@Test
	@Order(4)
	public void removeDimentionsTest1() throws SQLException {
	
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByDimentionsTest1");
		assertEquals(3,commandProcessor.processor("remove 900 800 124 224"));
		assertEquals(0,commandProcessor.processor("remove 1 1 1 1"));
	}
	
	@Test
	@Order(5)
    public void dumpTest2() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest2");
		assertEquals(7,commandProcessor.processor("dump"));
	}
	
	@Test
	@Order(6)
    public void removeNameTest1() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByNameTest1");
		assertEquals(2,commandProcessor.processor("remove rec"));
		assertEquals(0,commandProcessor.processor("remove R1"));
	
	}
	
	@Test
	@Order(7)
	public void dumpTest3() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest3");
		assertEquals(7,commandProcessor.processor("dump"));	
	}

	@Test
	@Order(8)
	public void intersectionsTest1() throws SQLException {
	
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IntersectionsTest1");
		assertEquals(6,commandProcessor.processor("intersections"));
	}
	
	@Test
	@Order(9)
	public void regionsearchTest1() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RegionSearchTest1");
		assertEquals(0,commandProcessor.processor("regionsearch 500 500 -100000000 -100000000"));
		assertEquals(4,commandProcessor.processor("regionsearch -100 -200 300 400"));
		
	}
	
	@Test
	@Order(10)
	public void insertTest2() throws SQLException {
		/* Rejected insertion part 1 
		 * Negative X ,Negative Y ,Negative W ,Negative H
		 * Negative X ,Negative Y ,W=Zero , H=Zero
		 * Negative X ,Negative Y ,Positive W ,Positive H
		 * Negative X ,Negative Y ,W=Zero ,Negative H
		 * Negative X ,Negative Y ,W=Zero ,Positive H
		 * Negative X ,Negative Y ,Positive W , H=Zero
		 * Negative X ,Negative Y ,Negative W , H=Zero
		 * Negative X ,Negative Y ,Positive W ,Negative H
		 * Negative X ,Negative Y ,Negative W ,Positive H
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 1~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest2");
		assertEquals(0,commandProcessor.processor("insert Rec1-inExist -10 -60 -1000000000 -1500000000"));
		assertEquals(0,commandProcessor.processor("insert Rec2-inExist -77 -55 0 0"));
		assertEquals(0,commandProcessor.processor("insert Rec3-inExist -2 -5 700000000 700000000"));
		assertEquals(0,commandProcessor.processor("insert Rec4-inExist -90 -80 0 -50"));
		assertEquals(0,commandProcessor.processor("insert Rec5-inExist -70 -40 0 20"));
		assertEquals(0,commandProcessor.processor("insert Rec6-inExist -30 -20 33 0"));
		assertEquals(0,commandProcessor.processor("insert Rec7-inExist -10 -15 -25 0"));
		assertEquals(0,commandProcessor.processor("insert Rec8-inExist -56 -66 52 -80"));
		assertEquals(0,commandProcessor.processor("insert Rec9-inExist -77 -88 -99 100"));
		
	}
	
	@Test
	@Order(11)
	public void regionsearchTest2() throws SQLException {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RegionSearchTest2");
		assertEquals(4,commandProcessor.processor("regionsearch 50 20 50 20"));
		assertEquals(0,commandProcessor.processor("regionsearch 7000 8000 -700000 -8000000"));
		assertEquals(4,commandProcessor.processor("regionsearch -11 -11 521478961 521478961"));
		
	}
	
	@Test
	@Order(12)
	public void removeDimentionsTest2() throws SQLException {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByDimentionsTest2");
		assertEquals(3,commandProcessor.processor("remove 22 22 6 6"));
		assertEquals(0,commandProcessor.processor("remove 50 45 45 65"));
		assertEquals(3,commandProcessor.processor("remove 50 30 40 20"));
		
	}
	
	@Test
	@Order(13)
	public void dumpTest4 () throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest4");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
	
	@Test
	@Order(14)
    public void removeNameTest2() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByNameTest2");
		assertEquals(2,commandProcessor.processor("remove Rec"));
		assertEquals(0,commandProcessor.processor("remove mRec"));
		
	}
	
	@Test
	@Order(15)
	public void dumpTest5 () throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest5");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
	
	@Test
	@Order(16)
	public void searchTest2() throws SQLException {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~SearchTest2");
		assertEquals(5,commandProcessor.processor("search largeRec"));
		assertEquals(5,commandProcessor.processor("search rec"));
		assertEquals(5,commandProcessor.processor("search exist"));
		assertEquals(5,commandProcessor.processor("search exist_2"));
		
	}
	
	@Test
	@Order(17)
	public void insertTest3() throws SQLException {
		/* Rejected insertion part 2 
		 * Positive X ,Negative Y ,Negative W ,Negative H
		 * Positive X ,Negative Y ,W=Zero , H=Zero
		 * Positive X ,Negative Y ,Positive W ,Positive H
		 * Positive X ,Negative Y ,W=Zero ,Negative H
		 * Positive X ,Negative Y ,W=Zero ,Positive H
		 * Positive X ,Negative Y ,Positive W , H=Zero
		 * Positive X ,Negative Y ,Negative W , H=Zero
		 * Positive X ,Negative Y ,Positive W ,Negative H
		 * Positive X ,Negative Y ,Negative W ,Positive H
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 2~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest3");
		assertEquals(0,commandProcessor.processor("insert notExist_Rec1 87 -88 -89 -90"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec2 71 -72 0 0"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec3 65 -66 67 68"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec4 73 -74 0 -75"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec5 50 -51 0 52"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec6 53 -54 55 0"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec7 60 -61 -62 0"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec8 90 -91 92 -93"));
		assertEquals(0,commandProcessor.processor("insert notExist_Rec9 94 -95 -96 97"));

	}
	
	@Test
	@Order(18)
	public void dumpTest6() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest6");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
	
	@Test
	@Order(19)
	public void searchTest3() throws SQLException {
	
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~SearchTest3");
		assertEquals(5,commandProcessor.processor("search LRec"));
		assertEquals(5,commandProcessor.processor("search large_Rec"));
		assertEquals(5,commandProcessor.processor("search smallRec"));
		assertEquals(5,commandProcessor.processor("search L_Rec"));
		
		
	}
	
	@Test
	@Order(20)
	public void insertTest4() throws SQLException {
		/* Rejected insertion part 3 
		 * Negative X ,Positive Y ,Negative W ,Negative H
		 * Negative X ,Positive Y ,W=Zero , H=Zero
		 * Negative X ,Positive Y ,Positive W ,Positive H
		 * Negative X ,Positive Y ,W=Zero ,Negative H
		 * Negative X ,Positive Y ,W=Zero ,Positive H
		 * Negative X ,Positive Y ,Positive W , H=Zero
		 * Negative X ,Positive Y ,Negative W , H=Zero
		 * Negative X ,Positive Y ,Positive W ,Negative H
		 * Negative X ,Positive Y ,Negative W ,Positive H
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 3~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest4");
		assertEquals(0,commandProcessor.processor("insert rec_1 -2 3 -4 -5"));
		assertEquals(0,commandProcessor.processor("insert rec_2 -6 7 0 0"));
		assertEquals(0,commandProcessor.processor("insert rec_3 -8 9 10 11"));
		assertEquals(0,commandProcessor.processor("insert rec_4 -12 13 0 -14"));
		assertEquals(0,commandProcessor.processor("insert rec_5 -15 16 0 17"));
		assertEquals(0,commandProcessor.processor("insert rec_6 -18 19 20 0"));
		assertEquals(0,commandProcessor.processor("insert rec_7 -21 22 -23 0"));
		assertEquals(0,commandProcessor.processor("insert rec_8 -24 25 26 -27"));
		assertEquals(0,commandProcessor.processor("insert rec_9 -28 29 -30 31"));
		
	}
	
	@Test
	@Order(21)
    public void removeNameTest3() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByNameTest3");
		assertEquals(2,commandProcessor.processor("remove smallRec"));
		assertEquals(0,commandProcessor.processor("remove Rectangle"));
		assertEquals(2,commandProcessor.processor("remove r50"));
		
		
	}
	
	@Test
	@Order(22)
	public void dumpTest7() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest7");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
	
	@Test
	@Order(23)
	public void insertTest5() throws SQLException {
		/* Rejected insertion part 4 
		 * Positive X ,Positive Y ,Negative W ,Negative H
		 * Positive X ,Positive Y ,W=Zero , H=Zero
		 * Positive X ,Positive Y ,W=Zero ,Negative H
		 * Positive X ,Positive Y ,W=Zero ,Positive H
		 * Positive X ,Positive Y ,Positive W , H=Zero
		 * Positive X ,Positive Y ,Negative W , H=Zero
		 * Positive X ,Positive Y ,Positive W ,Negative H
		 * Positive X ,Positive Y ,Negative W ,Positive H
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 4~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest5");
		assertEquals(0,commandProcessor.processor("insert Not_rec1 40 41 -42 -43"));
		assertEquals(0,commandProcessor.processor("insert Not_rec2 43 44 0 0"));
		assertEquals(0,commandProcessor.processor("insert Not_rec3 45 46 0 -47"));
		assertEquals(0,commandProcessor.processor("insert Not_rec4 48 49 0 50"));
		assertEquals(0,commandProcessor.processor("insert Not_rec5 51 52 53 0"));
		assertEquals(0,commandProcessor.processor("insert Not_rec6 54 55 -56 0"));
		assertEquals(0,commandProcessor.processor("insert Not_rec7 57 58 59 -60"));
		assertEquals(0,commandProcessor.processor("insert Not_rec8 61 62 -63 64"));
		
	}
	
	@Test
	@Order(24)
	public void removeDimentionsTest3() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByDimentionsTest3");
		assertEquals(3,commandProcessor.processor("remove 1000 1000 20 20"));
		assertEquals(0,commandProcessor.processor("remove 30 40 50 60"));
		assertEquals(3,commandProcessor.processor("remove 30 15 30 15"));
		
	
	}
	
	@Test
	@Order(25)
	public void dumpTest8() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest8");
		assertEquals(7,commandProcessor.processor("dump"));
	}
	
	@Test
	@Order(26)
	public void insertTest6() throws SQLException {
		/* Rejected insertion part 5 
		 * X=Zero ,Negative Y ,Negative W ,Negative H
		 * X=Zero ,Negative Y ,W=Zero , H=Zero
		 * X=Zero ,Negative Y ,Positive W ,Positive H
		 * X=Zero ,Negative Y ,W=Zero ,Negative H
		 * X=Zero ,Negative Y ,W=Zero ,Positive H
		 * X=Zero ,Negative Y ,Positive W , H=Zero
		 * X=Zero ,Negative Y ,Negative W , H=Zero
		 * X=Zero ,Negative Y ,Positive W ,Negative H
		 * X=Zero ,Negative Y ,Negative W ,Positive H
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 5~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest6");
		assertEquals(0,commandProcessor.processor("insert NotExist-1 0 -65 -66 -67"));
		assertEquals(0,commandProcessor.processor("insert NotExist-2 0 -68 0 0"));
		assertEquals(0,commandProcessor.processor("insert NotExist-3 0 -69 70 71"));
		assertEquals(0,commandProcessor.processor("insert NotExist-4 0 -72 0 -73"));
		assertEquals(0,commandProcessor.processor("insert NotExist-5 0 -74 0 75"));
		assertEquals(0,commandProcessor.processor("insert NotExist-6 0 -76 77 0"));
		assertEquals(0,commandProcessor.processor("insert NotExist-7 0 -78 -79 0"));
		assertEquals(0,commandProcessor.processor("insert NotExist-8 0 -80 81 -82"));
		assertEquals(0,commandProcessor.processor("insert NotExist-9 0 -83 -84 85"));
		
	}
	
	@Test
	@Order(27)
	public void intersectionsTest2() throws SQLException {
	
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IntersectionsTest2");
		assertEquals(6,commandProcessor.processor("intersections"));
		
	
	}
	
	@Test
	@Order(28)
	public void dumpTest9() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest9");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
	
	@Test
	@Order(29)
	public void insertTest7() throws SQLException {
		/* Rejected insertion part 6 
		 * Negative X ,Y=Zero ,Negative W ,Negative H
		 * Negative X ,Y=Zero ,W=Zero , H=Zero
		 * Negative X ,Y=Zero ,Positive W ,Positive H
		 * Negative X ,Y=Zero ,W=Zero ,Negative H
		 * Negative X ,Y=Zero ,W=Zero ,Positive H
		 * Negative X ,Y=Zero ,Positive W , H=Zero
		 * Negative X ,Y=Zero ,Negative W , H=Zero
		 * Negative X ,Y=Zero ,Positive W ,Negative H
		 * Negative X ,Y=Zero ,Negative W ,Positive H
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 6~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest7");
		assertEquals(0,commandProcessor.processor("insert In_Exist1 -100 0 -101 -102"));
		assertEquals(0,commandProcessor.processor("insert In_Exist2 -103 0 0 0"));
		assertEquals(0,commandProcessor.processor("insert In_Exist3 -104 0 105 106"));
		assertEquals(0,commandProcessor.processor("insert In_Exist4 -107 0 0 -108"));
		assertEquals(0,commandProcessor.processor("insert In_Exist5 -109 0 0 110"));
		assertEquals(0,commandProcessor.processor("insert In_Exist6 -111 0 112 0"));
		assertEquals(0,commandProcessor.processor("insert In_Exist7 -113 0 -114 0"));
		assertEquals(0,commandProcessor.processor("insert In_Exist8 -115 0 116 -117"));
		assertEquals(0,commandProcessor.processor("insert In_Exist9 -118 0 -119 120"));
		

	}
	
	@Test
	@Order(30)
	public void regionsearchTest3() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RegionSearchTest3");
		assertEquals(4,commandProcessor.processor("regionsearch 10 10 10 10"));
		assertEquals(4,commandProcessor.processor("regionsearch 0 0 70000000 800000"));
		assertEquals(0,commandProcessor.processor("regionsearch 400 400 -200 -200"));
		assertEquals(4,commandProcessor.processor("regionsearch -100000000 -100000000 500000000 500000000"));
		
		
		
	}
	
	@Test
	@Order(31)
	public void dumpTest10() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest10");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
	
	@Test
	@Order(32)
	public void insertTest8() throws SQLException {
		/* Rejected insertion part 7 
		 * X=Zero ,Y=Zero ,Negative W ,Negative H
		 * X=Zero ,Y=Zero ,W=Zero , H=Zero
		 * X=Zero ,Y=Zero ,W=Zero ,Negative H
		 * X=Zero ,Y=Zero ,W=Zero ,Positive H
		 * X=Zero ,Y=Zero ,Positive W , H=Zero
		 * X=Zero ,Y=Zero ,Negative W , H=Zero
		 * X=Zero ,Y=Zero ,Positive W ,Negative H
		 * X=Zero ,Y=Zero ,Negative W ,Positive H
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 7~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest8");
		assertEquals(0,commandProcessor.processor("insert Rec_1 0 0 -121 -122"));
		assertEquals(0,commandProcessor.processor("insert Rec_2 0 0 0 0"));
		assertEquals(0,commandProcessor.processor("insert Rec_3 0 0 0 -123"));
		assertEquals(0,commandProcessor.processor("insert Rec_4 0 0 0 124"));
		assertEquals(0,commandProcessor.processor("insert Rec_5 0 0 125 0"));
		assertEquals(0,commandProcessor.processor("insert Rec_6 0 0 -126 0"));
		assertEquals(0,commandProcessor.processor("insert Rec_7 0 0 127 -128"));
		assertEquals(0,commandProcessor.processor("insert Rec_8 0 0 -129 130"));
		
	}
	
	@Test
	@Order(33)
	public void searchTest4() throws SQLException {
	
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~SearchTest4");
		assertEquals(5,commandProcessor.processor("search r1"));
		assertEquals(5,commandProcessor.processor("search 1MRec"));
		assertEquals(5,commandProcessor.processor("search r50"));
		assertEquals(5,commandProcessor.processor("search Rec"));
		
		
	}
	
	@Test
	@Order(34)
	public void dumpTest11() throws SQLException{
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest11");
		assertEquals(7,commandProcessor.processor("dump"));
	
	}
	
	@Test
	@Order(35)
	public void insertTest9() throws SQLException {
		/* Rejected insertion part 8
		 * X+W>1024  && Y+H<=1024
		 * X+W<=1024 && Y+H>1024
		 * X+W>1024  && Y+H>1024
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 8~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest9");
		assertEquals(0,commandProcessor.processor("insert R_1 1000 24 500 1000"));
		assertEquals(0,commandProcessor.processor("insert R_2 700 600 300 800"));
		assertEquals(0,commandProcessor.processor("insert R_3 900 400 200 1600"));
		
	}
	
	@Test
	@Order(36)
	public void removeDimentionsTest4() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByDimentionsTest4");
		assertEquals(3,commandProcessor.processor("remove 68 12 60 14"));
		assertEquals(0,commandProcessor.processor("remove 150 90 80 100"));
		
	}
	
	@Test
	@Order(37)
	public void dumpTest12() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest12");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
	
	@Test
	@Order(38)
	public void insertTest10() throws SQLException {
		/* Rejected insertion part 9
		 * Insert any name that begins with any character, except letters,
		 * Positive X ,Positive Y ,Positive W , H=Positive
		 */
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Rejected Insertion part 9~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~InsertTest10");
		assertEquals(0,commandProcessor.processor("insert 22rec 10 20 30 40"));
		assertEquals(0,commandProcessor.processor("insert !insert 15 25 35 45"));
		assertEquals(0,commandProcessor.processor("insert `rec1 55 65 75 85"));
		assertEquals(0,commandProcessor.processor("insert ~r1 95 105 110 115"));
		assertEquals(0,commandProcessor.processor("insert @r2 125 135 145 155"));
		assertEquals(0,commandProcessor.processor("insert #r3 165 175 185 195"));
		assertEquals(0,commandProcessor.processor("insert $r4 200 205 210 215"));
		assertEquals(0,commandProcessor.processor("insert %r5 220 225 230 235"));
		assertEquals(0,commandProcessor.processor("insert ^r6 240 245 250 255"));
		assertEquals(0,commandProcessor.processor("insert &r7 260 265 270 275"));
		assertEquals(0,commandProcessor.processor("insert *r8 280 285 290 295"));
		assertEquals(0,commandProcessor.processor("insert +r9 300 305 310 315"));
		assertEquals(0,commandProcessor.processor("insert -r0 320 325 330 335"));
		assertEquals(0,commandProcessor.processor("insert /R1 340 345 350 355"));
		assertEquals(0,commandProcessor.processor("insert _R2 360 365 370 375"));
		assertEquals(0,commandProcessor.processor("insert ;R3 380 385 390 395"));
		assertEquals(0,commandProcessor.processor("insert :R4 400 405 410 415"));
		assertEquals(0,commandProcessor.processor("insert \\rec 420 425 430 435"));
		assertEquals(0,commandProcessor.processor("insert |rec2 440 445 450 455"));
		assertEquals(0,commandProcessor.processor("insert <rec3 460 465 470 475"));
		assertEquals(0,commandProcessor.processor("insert >rec4 480 485 490 495"));
		assertEquals(0,commandProcessor.processor("insert ?rec5 2 4 6 8"));
		assertEquals(0,commandProcessor.processor("insert ,rec6 10 12 14 16"));
		assertEquals(0,commandProcessor.processor("insert .rec7 18 20 22 24"));
		assertEquals(0,commandProcessor.processor("insert {rec8 26 28 30 32"));
		assertEquals(0,commandProcessor.processor("insert }rec9 34 36 38 40"));
		assertEquals(0,commandProcessor.processor("insert =R5 42 44 46 48"));
		assertEquals(0,commandProcessor.processor("insert (R6 50 52 54 56"));
		assertEquals(0,commandProcessor.processor("insert )R7 58 60 62 64"));
		assertEquals(0,commandProcessor.processor("insert 'R8 66 68 70 72"));
		
		
	}
	
	@Test
	@Order(39)
    public void removeNameTest4() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RemoveByNameTest4");
		assertEquals(2,commandProcessor.processor("remove r2"));
		assertEquals(0,commandProcessor.processor("remove r2r"));
		
	
	}
	
	@Test
	@Order(40)
	public void intersectionsTest3() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IntersectionsTest3");
		assertEquals(6,commandProcessor.processor("intersections"));
		
	}
	
	
	@Test
	@Order(41)
	public void dumpTest13() throws SQLException {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DumpTest13");
		assertEquals(7,commandProcessor.processor("dump"));
		
	}
 }
