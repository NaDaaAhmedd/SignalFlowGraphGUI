package LinearControl;

import java.util.ArrayList;

public class MyGraph {
    private final int start;
    private final int end;
    private final ArrayList<ArrayList<Edge>> adjlist;
    private final ArrayList<Edge> path = new ArrayList<>();
    private final ArrayList<ArrayList<Edge>> allpaths = new ArrayList<>();
    private final ArrayList<ArrayList<Edge>> loops = new ArrayList<>();
    private final boolean[] isVisited;
    private final ArrayList<Integer> list = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> finalNonTouched = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> nonTouched = new ArrayList<>();
    private int size;
    private double TransferFunction;

    MyGraph(int numVertex, int start, int end) {
        adjlist = new ArrayList<>();
        for (int i = 0; i < numVertex; i++) {
            adjlist.add(new ArrayList<>());
        }
        this.start = start;
        this.end = end;
        isVisited = new boolean[numVertex];
    }

    public boolean addEdge(int from, int to, double weight) {
    	Edge tmp = new Edge(from,to,weight);
    	for (Edge g : adjlist.get(from)) {
    		if (g.getTo() == to)
    			return false;
    	}
        adjlist.get(from).add(tmp);
        return true;
    }

    private void findpaths(int s) {
        isVisited[s] = true;
        if (s == end) {
            allpaths.add((ArrayList<Edge>) path.clone());
            isVisited[s] = false;
            return;
        }
        for (Edge ed : adjlist.get(s)) {
            if (!isVisited[ed.getTo()]) {
                path.add(ed);
                findpaths(ed.getTo());
                path.remove(ed);
            } else {
                ArrayList<Edge> temp = (ArrayList<Edge>) path.clone();
                for (int i = 0; i < path.size(); i++) {
                    if (path.get(i).getFrom() != ed.getTo()) {
                        temp.remove(path.get(i));
                    } else break;
                }
                temp.add(ed);
                loops.add(temp);
            }
        }
        isVisited[s] = false;
    }

    private double getGain(ArrayList<Edge> arr) {
        double gainnum = 1;
        for (Edge ed : arr) {
            gainnum *= ed.getWeight();
        }
        return gainnum;
    }

    private void twoNonTouchingLoops() {
        ArrayList<Double> gains = new ArrayList<>();
        boolean f = true;
        for (int i = 0; i < loops.size(); i++) {
            for (int j = i + 1; j < loops.size(); j++) {
                for (Edge ed1 : loops.get(i)) {
                    for (Edge ed2 : loops.get(j)) {
                        if (ed1.getFrom() == ed2.getFrom() || ed1.getTo() == ed2.getTo()
                                || ed1.getFrom() == ed2.getTo() || ed1.getTo() == ed2.getFrom()) {
                            f = false;
                        }
                    }
                }
                if (f) {
                    ArrayList<Integer> t = new ArrayList<Integer>();
                    t.add(i);
                    t.add(j);
                    nonTouched.add((ArrayList<Integer>) t.clone());
                    gains.add(getGain(loops.get(i)) * getGain(loops.get(j)));
                }
                f = true;
            }
        }
    }

    public double solveSFG() {
        findpaths(start);
        ArrayList<ArrayList<Edge>> temp = (ArrayList<ArrayList<Edge>>) loops.clone();
        for (int j = 0; j < temp.size(); j++) {
            for (int i = j + 1; i < temp.size(); i++) {
                int c = 0;
                for (Edge e : temp.get(j)) {
                    if (temp.get(i).contains(e)) {
                        c++;
                    }
                }
                if (c == temp.get(j).size()) loops.remove(temp.get(i));
            }
        }
        twoNonTouchingLoops();
        size = loops.size();
        solve(0);
        Double delta = getDelta(loops, finalNonTouched);
        double[] deltas = new double[allpaths.size()];
        for (int i = 0; i < allpaths.size(); i++) {
            ArrayList<ArrayList<Edge>> temploop = new ArrayList<>();
            for (int j = 0; j < loops.size(); j++) {
                boolean f = true;
                for (Edge ed1 : allpaths.get(i)) {
                    for (Edge ed2 : loops.get(j)) {
                        if (ed1.getFrom() == ed2.getFrom() || ed1.getTo() == ed2.getTo()
                                || ed1.getFrom() == ed2.getTo() || ed1.getTo() == ed2.getFrom()) {
                            f = false;
                        }
                    }

                }
                if (f)
                    temploop.add(loops.get(j));
            }
            size = temploop.size();
            finalNonTouched.clear();
            solve(0);
            deltas[i] = getDelta(temploop, finalNonTouched);
        }

        for (int i = 0; i < allpaths.size(); i++) {
            TransferFunction += getGain(allpaths.get(i)) * deltas[i];
        }
        TransferFunction /= delta;
        return TransferFunction;
    }

    private double getDelta(ArrayList<ArrayList<Edge>> loops, ArrayList<ArrayList<Integer>> finalNonTouched) {
        Double delta = 1.0;
        for (ArrayList<Edge> loop : loops) {
            delta -= getGain(loop);
        }
        for (ArrayList<Integer> elem : finalNonTouched) {
            double gain = 1;
            for (int in : elem) {
                gain *= getGain(loops.get(in));
            }
            if (elem.size() % 2 == 0)
                delta += gain;
            else
                delta -= gain;
        }
        return delta;
    }

    private void solve(int idx) {
        if (idx == size) {
            boolean flag = true;
            if (list.size() == 1 || list.size() == 0)
                return;
            for (int i = 0; i < list.size(); i++) {
                int c = 0;
                for (int j = 0; j < nonTouched.size(); j++) {
                    if (nonTouched.get(j).contains(list.get(i))) {
                        for (int k = i + 1; k < list.size(); k++) {
                            if (nonTouched.get(j).contains(list.get(k))) {
                                c++;
                                break;
                            }
                        }
                    }

                }
                if (c != list.size() - i - 1) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                finalNonTouched.add((ArrayList<Integer>) list.clone());
            return;
        }
        list.add(idx);
        solve(idx + 1);
        list.remove(list.size() - 1);
        solve(idx + 1);
    }

}
