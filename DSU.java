	static class DSU
	{
		int[] set;
		
		DSU(int size)
		{
			set = new int[size];
			for(int i = 0; i < size; i++) set[i] = i;
		}
		
		int findRoot(int pos)
		{
			if(set[pos] == pos) return pos;
			return set[pos] = findRoot(set[pos]);
		}
		
		boolean isInSameSet(int pos1, int pos2)
		{
			return findRoot(pos1) == findRoot(pos2);
		}
		
		boolean union(int pos1, int pos2)
		{
			int c1 = findRoot(pos1);
			int c2 = findRoot(pos2);
			if(c1 == c2) return false;
			set[c2] = c1;
			return true;
		}
	}
