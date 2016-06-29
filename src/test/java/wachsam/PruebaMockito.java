package wachsam;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class PruebaMockito {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1()  {
	        //  create mock
	        MyClass test = Mockito.mock(MyClass.class);

	        // define return value for method getUniqueId()
	        when(test.getUniqueId()).thenReturn(43);

	        // use mock in test....
	        assertEquals(test.getUniqueId(), 43);
	}
	@Test
	public void testMoreThanOneReturnValue()  {
	        Iterator i= mock(Iterator.class);
	        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
	        String result=i.next()+" "+i.next();
	        //assert
	        assertEquals("Mockito rocks", result);
	}
	@Test
	public void testReturnValueDependentOnMethodParameter()  {
	        Comparable c= mock(Comparable.class);
	        when(c.compareTo("Mockito")).thenReturn(1);
	        when(c.compareTo("Eclipse")).thenReturn(2);
	        //assert
	        assertEquals(1,c.compareTo("Mockito"));
	}
	
	@Test
	public void testReturnValueInDependentOnMethodParameter()  {
	        Comparable c= mock(Comparable.class);
	        when(c.compareTo(anyInt())).thenReturn(-1);
	        //assert
	        assertEquals(-1 ,c.compareTo(9));
	}
	
	@Test
	public void testReturnValueDependentOnMethodParameterBis()  {
	        Comparable c= mock(Comparable.class);
	        when(c.compareTo(isA(MyClass.class))).thenReturn(0);
	        //assert
	        MyClass todo = new MyClass();
	        assertEquals(0 ,c.compareTo(new MyClass()));
	}
	@Test(expected=IOException.class)
	public void testForIOException() throws IOException {
	        // create an configure mock
	        OutputStream mockStream = mock(OutputStream.class);
	        doThrow(new IOException()).when(mockStream).close();

	        // use mock
	        OutputStreamWriter streamWriter= new OutputStreamWriter(mockStream);
	        streamWriter.close();
	}
	
	@Test
	public void testVerify()  {
	        // create and configure mock
	        MyClass test = Mockito.mock(MyClass.class);
	        when(test.getUniqueId()).thenReturn(43);


	        // call method testing on the mock with parameter 12
	        test.testing(12);
	        test.getUniqueId();
	        test.getUniqueId();


	        // now check if method testing was called with the parameter 12
	        verify(test).testing(Matchers.eq(12));

	        // was the method called twice?
	        verify(test, times(2)).getUniqueId();

	        // other alternatives for verifiying the number of method calls for a method
	        verify(test, never()).testing("never called");
	        test.testing("called at least once");
	        verify(test, atLeastOnce()).testing("called at least once");
	        //verify(test, atLeast(2)).testing("called at least twice");
	        //verify(test, times(5)).testing("called five times");
	        //verify(test, atMost(3)).testing("called at most 3 times");
	}

	
	private class MyClass{
		public int getUniqueId(){
			return -1;
		}
		public void testing(int i){
			
		}
        public void testing(String i){
			
		}
	}

}
