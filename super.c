#include <stdbool.h>
#include <stdlib.h>
#include <string.h>

#include "sc1.h"

/* -- CONSTANTS -- */
#define UNKNOWN 15

/* -- Function Declaration -- */
int comb(int sorted[], int len);
void sort(int data1[], int data2[], int len); 
int solve(int unk, int n, int x);
int check(int t);
void rotate();
int isMahoJin();

/* -- Variable Declaration -- */
int nokori[N];
int *sorted;
int *index_;
int *used;


int main()
{
	/* -- variables counter in both column and row  */
	int line[N] = {};
	int raw[N] = {};
	/* -- Coutner Variables */
	int i, j;
	int count = 0;
	used = malloc(N * N * sizeof(int));

	/* -- Temporary Container. L is line(column), R is raw(row). -- */ 
	int * sortedL = malloc((N + 1) * sizeof(int));
	int * sortedR = malloc((N + 1) * sizeof(int));
	int indexL[] = {0,1,2,3,4,5};
	int indexR[] = {0,1,2,3,4,5};
	int reversed = false;

	/* -- Initialize Variable -- */
	sorted = malloc((N + 1) * sizeof(int));

	input(s);
	/* -- Search Variable(has Zero Value) and Log its position -- */
	for (i = 0; i < N; i++) {
		for (j = 0; j < N; j++) {
			if (s[i][j] == 0)  {
				line[i]++;
			}
			else {
				/** Mark Variable Pos **/
				used[s[i][j] - 1] = true;
			}
			if (s[j][i] == 0) {
				raw[i]++;
			}
		}
	}

	for (i = 0; i < N * N; i++) {
		if (! used[i]) {
			nokori[count++] = i + 1;
		}
	}

	memcpy(sortedL, line, N+1);
	memcpy(sortedR, raw, N+1);

	sort(sortedL, indexL, N);
	sort(sortedR, indexR, N);

	if (comb(sortedL, N) > comb(sortedR, N)) {
		reversed = true;
		rotate();
		index_ = indexR;
		sorted = sortedR;
	} else {
		index_ = indexL;
		sorted = sortedL;
	}

	solve(sorted[0], 0, 0);
	if (reversed) {
		rotate();
	}

	output(s);
	
}

/* -- Function Impelementations */
int comb(int sorted[], int len)
{
	int sum = 0;
	int buf;
	int unk = UNKNOWN;
	int i, j;

	for (i = 0; i < len; i++) {
			buf = 1;
			for (j = 0; j < sorted[i]; j++) {
				buf *= unk;
				unk--;
			}
			sum += buf;
	}

	return sum;
}


/* -- Sort data1 and data2. data2 is sorted according to data1 -- */
void sort(int data1[], int data2[], int len)
{
	int i, j;
	int tmp, tmp2;
	for (i = 0; i < len - 1; i++) {
		for (j = 0; j < len - i - 1; j++) {
			if (data1[j] > data1[j+1]) {
				tmp = data1[j];
				tmp2 = data2[j];

				data1[j] = data1[j+1];
				data1[j+1] =  tmp;
				data2[j] = data2[j+1];
				data2[j+1] = tmp2;
			}
		}
	}
}

/* -- Solve -- */
int solve(int unk, int n, int x) {
	int i;
	if (n == N) {
		return isMahoJin();
	}
	if (unk == 0) {
		return check(index_[n]) && solve(sorted[n+1], n+1, 0);
	}
	while (s[index_[n]][x] != 0) {
		x++;
	}
	for (i = 0; i < UNKNOWN; i++) {
		if (used[i]) {
			continue;
		}
		used[i] = true;
		s[index_[n]][x] = nokori[i];
		if (solve(unk-1, n, x+1)) {
			return true;
		}
		s[index_[n]][x] = 0;
		used[i] = false;
	}

	return false;
}

/* -- Check -- */
int check(int t) {
	int sum=0;
	int i = 0;
	for(i=0;i<N;i++){
		sum+=s[t][i];
	}
	return sum==111;
}

/* -- Rotate MahoJin -- */
void rotate() {
		int i,j;
		int newarray[N][N];
		for(i=0;i<N;i++){
			for(j=0;j<N;j++){
				newarray[i][j]=s[j][i];
				}
		}
		int(*s)[N] = newarray;
}

/* -- Check is MahoJin? -- */
int isMahoJin() {
	int sum1;
	int sum2;
	int i = 0, j = 0;
	for(i=0;i<N;i++){
		sum1=0;
		sum2=0;
		for(j=0;j<N;j++) {
			sum1+=s[i][j];
			sum2+=s[j][i];
		}
		if(sum1!=111||sum2!=111) { 
			return false;
		}
	}
	sum1=0;
	sum2=0;
	for(j=0;j<N;j++){
		sum1+=s[j][j];
		sum2+=s[j][N-j-1];
	}
	if(sum1!=111||sum2!=111) {
		return false;
	}
	return true;
}


