
//package threadfun;

/*
Zachary Lapointe
CS2790
Java program to run threads and use them to find prime numbers
Format is java Threadfun <# of threads> <number to search to>
*/


class Primefinder implements Runnable{
    
    private int upper;
    private int lower;
    private int threadid;
    
    public Primefinder(int N, int M, int threadid){
        this.upper = (threadid+1) * (N/M);	//Upper boundary
        this.lower = threadid * (N/M)+1;	//Lower boundary
	this.threadid = threadid;
    }

    public boolean isprime(int i)
    {
	boolean check = true;
	for(int j=2; j<i; j++){
		if(i%j == 0){	//Slow and lazy way to find prime, dividing by every number before it
			check = false;
			break;
		}
	}
	return check;
    }
    
    public void run(){
        for(int i = lower; i<= upper; i++)
		if(isprime(i))	//Check if prime
			System.out.println("thread "+threadid+" found prime "+i);
    }
    
}

public class Threadfun {

    public static void main(String[] args) {
	
        if(args.length > 0){	//Check for an arg
            if(Integer.parseInt(args[0]) < 0)	//Check arg is greater than 0
                System.err.println(args[0] + " must be >= 0.");
            else{
                int TopNum = Integer.parseInt(args[1]);		//Top number you are searching to
		int NumThreads = Integer.parseInt(args[0]);	//Number of threads to create

                Thread[] thrdarray = new Thread[NumThreads];	//Array of thread objects

		for(int i=0; i < NumThreads; i++) {
			thrdarray[i] = new Thread(new Primefinder(TopNum, NumThreads, i));   //Create a new thread object in each element
                	thrdarray[i].start();	//Start the threads
		}
                try{
			for(int i=0; i < NumThreads; i++) {
	                        thrdarray[i].join();	//End the threads
                        }
                }catch(InterruptedException ie){} //Example code had this figure i would keep it in
            }
        }
	else System.err.println("usage: Threadfun <integer value> <integer value>");	//Shows format if you fail
    }

}
    
