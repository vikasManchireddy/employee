package vikas;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.springframework.core.SpringVersion;

public class VersionChecker
{
	String k="Anurag";
	public VersionChecker(){
    	
		
		
    }
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return k;
	}
    public static void main(String [] args)
    {
        /*System.out.println("version: " + SpringVersion.getVersion());
        VersionChecker vc=new VersionChecker(50,"vikas",new String{"hello","there"});
        */
        
        VersionChecker v=new VersionChecker();
     
        
       System.out.println(v);
        
    }
}
