package knapsack;
import java.util.Arrays;

public class Knapsack1 {

	public static void main(String[] args) {
		float c = 40;
		float[] w = { 3, 2, 6, 8, 9, 10, 12 };
		float[] v = { 4, 9, 3, 6, 3, 2, 6 };
		float[] x = new float[w.length];
		System.out.println("背包容量：" + c);
		System.out.println(Arrays.toString(w));
		System.out.println(Arrays.toString(v));
		System.out.println(Arrays.toString(x));
		System.out.println("最大价值：" + tanxin(c, w, v, x));
	}

	public static float tanxin(float c, float[] w, float[] v, float[] x) {
		int n = v.length;
		Element[] d = new Element[n];
		for (int i = 0; i < n; i++)
			d[i] = new Element(w[i], v[i], i);
		sort(d);
		int i;
		float opt = 0;
		for (i = 0; i < n; i++)
			x[i] = 0;
		for (i = 0; i < n; i++) {
			if (d[i].w > c)
				break;
			x[d[i].i] = 1;
			opt += d[i].v;
			c -= d[i].w;
		}
		if (i < n) {
			x[d[i].i] = c / d[i].w;
			opt += x[d[i].i] * d[i].v;
		}
		return opt;

	}

	private static void sort(Element[] d) {
		for (int i = 0; i < d.length; i++) {
			float max = d[i].v / d[i].w;
			int index = i;
			for (int j = i; j < d.length; j++) {
				if (max < d[j].v / d[j].w) {
					max = d[j].v / d[j].w;
					index = j;
				}
			}
			Element t;
			t = d[index];
			d[index] = d[i];
			d[i] = t;
		}
	}
}
