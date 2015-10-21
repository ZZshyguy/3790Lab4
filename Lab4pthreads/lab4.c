/*
Compiling:
gcc -std=c99 -c lab4.c
gcc lab4.o -o threadfun -lpthread

Zachary Lapointe
CS3790
Use pthreads to find primes
Format is ./threadfun <# of threads> <number to search to>
*/

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

void *runner(void *param);

int N;	//Number to search up to
int M;	//Number of threads

int main(int argc, char *argv[])
{
	
	M = atoi(argv[1]);
 	N = atoi(argv[2]);

	//printf("N= %d      M= %d\n",N,M);
	
	pthread_t tid[M];	//Array for pthread ids

	if(M<N){

		for(int i=0; i<M; i++) {
			pthread_create(&tid[i],NULL,runner,(void*)i);	//Start the threads
		}

		for(int i=0; i<M; i++) {
			pthread_join(tid[i],NULL);	//End the threads
		}

	}
	else printf("Format is : ./threadfun <# of threads> <max number>\n");

}

int isprime(int i)
{
	
	int check = 1;
	for(int j=2; j<i; j++){
		if(i%j == 0){	//Slow, lazy way to check if prime number, divide by each number before it
			check = 0;
			break;
		}
	}
	return check;

}

void *runner(void *param)
{

	int threadid = (int)param;
	int lowerbound = threadid * (N/M)+1;	//Lower boundary to search
	int upperbound = (threadid+1)*(N/M);	//Upper boundary to search
	for(int i = lowerbound; i<= upperbound; i++)
		if(isprime(i))	//Check if prime
			printf("thread %d found prime %d\n", threadid, i); //Print id and prime found
	pthread_exit(0);

}

