package knapsack;

import java.util.Arrays;

public class Knapsack2 {

	int n;// 物品数量
	double c;// 背包容量
	double[] v = new double[n];// 各个物品的价值
	double[] w = new double[n];// 各个物品的重量
	double currentW = 0;// 当前背包重量
	double currentV = 0;// 当前背包中物品价值
	double maxV = 0;// 当前最优价值
	double[] perV;// 物品单位重量价值
	int[] order;// 物品编号
	int[] result;// 是否装入

	public Knapsack2(int nn, double cc, double[] ww, double[] vv) {
		n = nn;
		c = cc;
		w = ww;
		v = vv;
		perV = new double[n];
		order = new int[n];
		result = new int[n];
		for (int i = 0; i < n; i++) {
			order[i] = i + 1;
			perV[i] = v[i] / w[i];
			result[i] = 0;
		}
		// 按单位价值排序
		qsort(0, n - 1);
	}

	void qsort(int l, int r) {
		if (l < r) {
			int index = partition(l, r);
			qsort(l, index - 1);
			qsort(index + 1, r);
		}
	}

	int partition(int left, int right) {
		int i = left, j = right + 1;
		double bpv = perV[left];
		double bw = w[left];
		int bo = order[left];
		double bv = v[left];
		while (true) {
			while (perV[++i] > bpv && i < right);
			while (perV[--j] < bpv);
			if (i >= j)
				break;
			swap(i, j);
		}
		
		perV[left] = perV[j];
		perV[j] = bpv;
		
		v[left] = v[j];
		v[j] = bv;
		
		w[left] = w[j];
		w[j] = bw;
		
		order[left] = order[j];
		order[j] = bo;
		
		return j;
	}

	void swap(int i, int j) {
		double t = perV[i];
		perV[i] = perV[j];
		perV[j] = t;
		
		int m = order[i];
		order[i] = order[j];
		order[j] = m;
		
		t = w[i];
		w[i] = w[j];
		w[j] = t;
		
		t = v[i];
		v[i] = v[j];
		v[j] = t;	
	}

	// 回溯函数
	void backtrack(int t) {
		if (t > n - 1) {
			maxV = currentV;
			return;
		}
		// 将物品装进背包：此种情况的话
		if (currentW + w[t] <= c) {
			currentW += w[t];
			currentV += v[t];
			result[t] = 1;
			backtrack(t + 1);
			currentW -= w[t];
			currentV -= v[t];
		}
		if (bound(t + 1) > maxV)// 符合条件搜索右子数
			backtrack(t + 1);
	}

	// 计算上界函数

	// 算法剩余容量的情况下最多能装的价值
	double bound(int k) {
		double leftw = c - currentW;
		double bestV = currentV;
		while (k < n && w[k] <= leftw) {
			leftw -= w[k];
			bestV += v[k];
			k++;
		}
		if (k <= n - 1)
			bestV += v[k] / w[k] * leftw;
		return bestV;
	}

	public static void main(String[] args) {
		int n = 5;
		double c = 10;
		double[] w = { 2, 2, 6, 5, 5 }; // 每个物体的重量：5个物体
		double[] v = { 6, 3, 5, 4, 6 }; // 每个物体的价值
		Knapsack2 kn = new Knapsack2(n, c, w, v);
		long start = System.nanoTime();
		kn.backtrack(0);
		long end = System.nanoTime();
		System.out.println("背包容量：" + kn.c);
		System.out.println("最大价值：" + kn.maxV);
		System.out.println("物品编号："+Arrays.toString(kn.order));
//		System.out.println("物品重量："+Arrays.toString(kn.w));
//		System.out.println("物品价值："+Arrays.toString(kn.v));
//		System.out.println("物品单位重量价值："+Arrays.toString(kn.perV));
		System.out.println("物品是否装入："+Arrays.toString(kn.result));
		System.out.println("运行时间："+ (end - start) + "纳秒");
	}

}
