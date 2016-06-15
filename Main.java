class Square{
		int x,y,value=-1;
		Square(int x,int y){
			this.x=x;
			this.y=y;
		}
	}

public class Main {

	static final int N=6;		//N次魔法陣
	static final int MAX_E=15;	//未定のマス数
	static final int NN=N*N;
	static int s[][]={
			{ 6,-1,25,-1,35,-1},
			{11,20,-1,19, 7,21},
			{-1, 2,-1,-1,-1, 9},
			{-1,34,-1,-1,17,-1},
			{ 1,29,-1,23,-1,28},
			{27, 8, 5,31,16,-1},
	};
	static Square x[]=new Square[MAX_E];
	static int nokori[]=new int[15];
	static int used[];
	static int NE=1;	//NE=NotEquation（方程式にできない列・行の数もとい探索するマスの数(だから初期値1)）
	public static void main(String[] args) {
		int i,j,count1,count2,count3;
		int used[]=new int[NN];		//1～36までの数字で使っていない数字をチェックするための配列

//-1になっている部分を探す
		count3=0;
		for(i=0;i<N;i++){
			count1=0;
			count2=0;
			for(j=0;j<N;j++){
				if(s[i][j]==-1){
					count1++;	//行を見る
					x[count3++]=new Square(j,i);
				}
				if(s[j][i]==-1)count2++;	//列を見る
				else used[s[j][i]-1]=1;		//ついでに使った数字をチェック
			}
			if(count1==0){
				NE++;
			}
			if(count2==0){
				NE++;
			}
		}
		count1=0;
		count2=0;
		for(j=0;j<N;j++){
			if(s[j][j]==-1)count1++;	//ななめを見る
			if(s[N-j-1][j]==-1)count2++;
		}
		if(count1==0)NE++;
		if(count2==0)NE++;
		count1=0;
		for(i=0;i<NN;i++){
			if(used[i]==0){
				nokori[count1]=i+1;
				count1++;
			}
		}
		for(int num:nokori){
			System.out.print(num+",");
		}

//入力から行列を作成する

		double matrix[][]=new double [2*N+3][MAX_E+1];	//要素は0で初期化
		for(i=0;i<MAX_E;i++){
			matrix[x[i].y][i]=1;
			matrix[x[i].x+N][i]=1;
			if(x[i].y==x[i].x){
				matrix[MAX_E-3][i]=1;
				printf("a");
			}
			else if(x[i].x==N-1-x[i].y){
				matrix[MAX_E-2][i]=1;
			}
		}
		count1=0;
//111からの差分を求める．
		for(i=0;i<N;i++){
			int sum1=111;
			int sum2=111;
			for(j=0;j<N;j++){
				if(s[i][j]!=-1)sum1-=s[i][j];
				if(s[j][i]!=-1)sum2-=s[j][i];
			}
			matrix[i][MAX_E]=sum1;
			matrix[i+N][MAX_E]=sum2;
		}
		int sum1=111;
		int sum2=111;
		for(j=0;j<N;j++){
			if(s[j][j]!=-1)sum1-=s[j][j];
			if(s[N-j-1][j]!=-1)sum2-=s[j][j];
		}
		matrix[2*N][MAX_E]=sum1;
		matrix[2*N+1][MAX_E]=sum2;
		sum1=0;
		for(j=0;j<MAX_E;j++){
			sum1+=nokori[i];
		}
		matrix[2*N+2]=new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,sum1};

		print(matrix);
		toUpper(matrix);
		print(matrix);
		//dfs(NE,matrix);
	}
	static void toUpper(double matrix[][]){
//部分的に上三角行列を求める．
		int i,j,k;
		for(i=0;i<2*N+2;i++){
			double p=1/matrix[i][i];
			for(j=0;j<MAX_E+1;j++){
				matrix[i][j]*=p;
			}
		}
		for(i=0;i<2*N+2;i++){
			double p=matrix[i][i];
			for(j=0;j<MAX_E+1;j++){
				for(k=0;k<MAX_E+1;k++){
					matrix[i][j]*=p;
				}
			}
		}
	}
	static int dfs(int n,int matrix[][]){
		if(n==0){

		}
		else{
			int i;
			for(i=0;i<MAX_E;i++){
				used[i]=1;

				if(dfs(n-1,matrix)==1)return 1;

				used[i]=0;
			}
		}
		return -1;
	}

//JAVAとC言語の差を小さくするための悪あがき
	static void printf(String x,Object... a){
		System.out.printf(x,a);
	}
	static void printf(String x){
		System.out.printf(x);
	}
//pythonの糞やろー(でばっぐよう)
	static void print(double x[]){
		System.out.print("{");
		for(double i:x){
			System.out.printf("%1.0f,",i);
		}
		System.out.println("}");
	}
	static void print(Square x){
		System.out.print("("+x.x+","+x.y+")");
	}
	static void print(Square x[]){
		System.out.print("{");
		for(Square i:x){
			print(i);
			System.out.print(",");
		}
		System.out.println("}");
	}
	static void print(double x[][]){
		System.out.println("{");
		for(double i[]:x){
			print(i);
		}
		System.out.println("}");
	}
}
