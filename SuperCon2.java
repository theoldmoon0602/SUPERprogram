import java.util.Arrays;

public class SuperCon2 {
	static final int N=6;
	static final int UNKNOWN=15;
	static int nokori[]=new int[UNKNOWN];	//使ってない数字の配列
	static int sorted[]=new int[N+1];		//必ずN+1にしてください(Cだと範囲外参照しても例外ないんやったっけ？)
	static int index[];
	static int jin[][]={
			{ 6, 0,25, 0,35, 0},
			{11,20, 0,19, 7,21},
			{ 0, 2, 0, 0, 0, 9},
			{ 0,34, 0, 0,17, 0},
			{ 1,29, 0,23, 0,28},
			{27, 8, 5,31,16, 0},
	};
	static boolean used[]=new boolean[UNKNOWN];
	public static void main(String[] args) {
		int line[]=new int[N];	//それぞれの行・列の不定のマス数
		int raw[]=new int[N];
		int i,j;

		boolean used[]=new boolean[N*N];
//入力
		//input(jin);	本当はここでヘッダファイルのinputを実行して入力を受ける

//処理
		//long before =System.nanoTime();
		for(i=0;i<N;i++){
			for(j=0;j<N;j++){
				if(jin[i][j]==0)line[i]++;
				else used[jin[i][j]-1]=true;
				if(jin[j][i]==0)raw[i]++;
			}
		}
		int count=0;
		for(i=0;i<N*N;i++){
			if(!used[i])nokori[count++]=i+1;
		}
		int sortedL[]=Arrays.copyOf(line, N+1);
		int sortedR[]=Arrays.copyOf(raw, N+1);
		int IndexL[]={0,1,2,3,4,5};
		int IndexR[]={0,1,2,3,4,5};
		boolean reverced=false;
		sort(sortedL,IndexL);
		sort(sortedR,IndexR);
		if(comb(sortedL)>comb(sortedR)){
//列で探索するときは行と列を入れ替える
			//System.out.println("Reverce:");
			reverced=true;
			rotate();
			index=IndexR;
			sorted=sortedR;
		}
		else{
			index=IndexL;
			sorted=sortedL;
		}/*
		print(jin);
		print(sorted);
		print(index);
		print(nokori);
		*/
		solve(sorted[0],0,0);
		if(reverced){
			rotate();
		}
		//long after =System.nanoTime();
		print(jin);
		//System.out.println("実行時間："+(after-before)*0.000000001f+"秒");
	}

	static boolean solve(int unk,int n,int x){
		if(n==N)return isMahoJin();
		if(unk==0)return check(index[n])&&solve(sorted[n+1],n+1,0);
		while(jin[index[n]][x]!=0)x++;
		for(int i=0;i<UNKNOWN;i++){
			if(used[i])continue;
			used[i]=true;
			jin[index[n]][x]=nokori[i];
			if(solve(unk-1,n,x+1))return true;
			jin[index[n]][x]=0;
			used[i]=false;
		}
		return false;
	}

	static boolean check(int t){
		int sum=0;
		for(int i=0;i<N;i++){
			sum+=jin[t][i];
		}
		return sum==111;
	}
	static void sort(int data[],int data2[]){	//dataをソートしながらdata2もおなじように並び替える
		for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(data[j]>data[j+1]){
					int temp=data[j];
					int temp2=data2[j];
					data[j]=data[j+1];
					data[j+1]=temp;
					data2[j]=data2[j+1];
					data2[j+1]=temp2;
				}
			}
		}
	}

	static void rotate(){	//[i][j]を[j][i]に変換(行と列を入れ替える)
		int i,j;
		int newarray[][]=new int[N][N];
		for(i=0;i<N;i++){
			for(j=0;j<N;j++){
				newarray[i][j]=jin[j][i];
			}
		}
		jin=newarray;
	}

	static int comb(int sorted[]){	//コンビネーションで計算量を見積もる
		int sum=0,buf;
		int unk=UNKNOWN;
		for(int i=0;i<N;i++){
			buf=1;
			for(int j=0;j<sorted[i];j++){
				buf*=unk;
				unk--;
			}
			sum+=buf;
		}
		return sum;
	}
	static boolean isMahoJin(){
		int sum1;
		int sum2;
		for(int i=0;i<N;i++){
			sum1=0;
			sum2=0;
			for(int j=0;j<N;j++){
				sum1+=jin[i][j];
				sum2+=jin[j][i];
			}
			if(sum1!=111||sum2!=111)return false;
		}
		sum1=0;
		sum2=0;
		for(int j=0;j<N;j++){
			sum1+=jin[j][j];
			sum2+=jin[j][N-j-1];
		}
		if(sum1!=111||sum2!=111)return false;
		return true;
	}

	//pythonの糞やろー(でばっぐよう)
		static void print(int x[]){
			System.out.print("{");
			for(int i:x){
				System.out.printf("%2d,",i);
			}
			System.out.println("}");
		}
		static void print(int x[][]){
			System.out.println("{");
			for(int i[]:x){
				print(i);
			}
			System.out.println("}");
		}
}
