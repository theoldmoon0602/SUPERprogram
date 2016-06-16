#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>

#include "sc1.h"

#define UNKNOWN 15

int solve(int unknown_count,int col,int row);
int check(int col);
void sort(int data[],int data2[]);
void rotate();
int comb(int sorted[]);
int isMahoJin();

/* -- Variables -- */
int unused[UNKNOWN]; /* Unused numbers */
int used[UNKNOWN]; /* used flag of unsed numbers*/
int *sorted; 
int *pastIndex;

int main() {
	/* variables */
	int col[N]; /* number of variables in each cols */
	int row[N]; /* number of vairables in each rows */
	int i,j; 
	int unused_index; /* index of unused array */

	int colIndex[N]; /* index of position in unsorted col */
	int rowIndex[N];
	int used[N * N];

	int reversed; /* Flag is swapped col and row */

	/* input */
	input(s);

	for (i = 0; i < N; i++) {
		col[i] = 0;
		row[i] = 0;
		colIndex[i] = i;
		rowIndex[i] = i;

		for (j = 0; j < N; j++) {
			used[i*N+j] = 0;
		}
	}
	unused_index = 0;
	reversed = false;

	/* parse input */
	for (i=0;i<N;i++) {
		for (j=0;j<N;j++) {
			if (s[i][j]==0) {
				/* Vairable */
				col[i]++;
				row[j]++;
			} else {
				/* Value */
				used[s[i][j]-1]=true; /* Set Used Flag */
			}
		}
	}

	/* Find unused valued */
	for(i=0;i<N*N;i++){
		if(! used[i]) {
			unused[unused_index++]=i+1;
		}
	}

	/* sort col and row, and keep their past index */
	sort(col,colIndex);
	sort(row,rowIndex);

	if (comb(col) > comb(row)) {
		reversed = true;
		rotate();
		pastIndex = rowIndex;
		sorted = row;
	}
	else { 
		pastIndex = colIndex;
		sorted = col;
	}

	/* solve */
	solve(sorted[0],0,0);
	if (reversed) {
		rotate();
	}

	/* Output */
	output(s);
}

int solve(int unknown_count,int col,int row) {
	int i = 0;

	if (col == N) {
		return isMahoJin();
	}
	if (unknown_count == 0) {
		return check(pastIndex[col]) && solve(sorted[col+1], col+1, 0);
	}

	/* search row pos of variable */
	while (s[pastIndex[col]][row] != 0) {
		row++;
	}
	for (i=0; i<UNKNOWN; i++) {
		if (used[i]) {
			continue;
		}
		used[i] = true;
		s[pastIndex[col]][row] = unused[i];
		if (solve(unknown_count-1, col, row+1)) {
			return true;
		}
		s[pastIndex[col]][row] = 0;
		used[i] = false;
	}
	return false;
}

/* Check is correct col */
int check(int col){
	int sum=0, i = 0;
	for (i=0; i<N; i++) {
		sum+=s[col][i];
	}
	return sum==111;
}

/* Sort data1 and sort data2 in according to key of data1 */
void sort(int data[],int data2[]) {
	int i = 0, j = 0;
	int tmp = 0;
	for (i=0; i < N-1; i++) {
		for(j=0; j < N-i-1; j++){
			if (data[j] > data[j+1]) {
				tmp = data[j];
				data[j] = data[j+1];
				data[j+1] = tmp;

				tmp = data2[j];
				data2[j] = data2[j+1];
				data2[j+1] = tmp;
			}
		}
	}
}

/* Swap col and row */
void rotate() {
	int i = 0, j = 0;
	int newarray[N][N] = {};
	for(i=0;i<N;i++){
		for(j=0;j<N;j++){
			newarray[i][j]=s[j][i];
		}
	}
	for(i=0;i<N;i++){
		for(j=0;j<N;j++){
			s[i][j] = newarray[i][j];
		}
	}
}

int comb(int sorted[]) {
	int sum = 0, buf = 0;
	int unk = UNKNOWN;
	int i = 0, j = 0;

	for (i = 0; i < N; i++) {
		buf=1;
		for (j=0 ; j < sorted[i]; j++) {
			buf*=unk;
			unk--;
		}
		sum+=buf;
	}
	return sum;
}

/* Check is s the mahojin */
int isMahoJin(){
	int sum1 = 0, sum2 = 0;
	int i = 0, j = 0;
	for (i=0; i < N; i++) {
		sum1=0;
		sum2=0;
		for(j=0;j<N;j++){
			sum1+=s[i][j];
			sum2+=s[j][i];
		}
		if (sum1!=111 || sum2!=111) {
			return false;
		}
	}
	sum1=0;
	sum2=0;
	for (j=0; j < N; j++) {
		sum1+=s[j][j];
		sum2+=s[j][N-j-1];
	}
	if (sum1!=111 || sum2!=111) {
		return false;
	}
	return true;
}

