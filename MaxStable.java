package Code;


import ilog.concert.IloException;

import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.UnknownObjectException;


public class MaxStable {
	 int [][] M;
	 int n; //nombre de sommet
	 IloCplex model; //modele cplex
	 IloNumVar [] x; // variable
	 
		public MaxStable(int [][] M) throws IloException {
			super();
	        this.M=M;	
	        n=M.length;
			model=new IloCplex();
			creationModel();
			//System.out.println(model.toString());
			
		}

		private void creationModel() throws IloException {
			// TODO Auto-generated method stub
			creationVariables();
			creationConstraints();
			creationObjective();
		}
		private void creationVariables() throws IloException {
			// TODO Auto-generated method stub
			x=model.boolVarArray(n);
		}
		public void creationConstraints() throws IloException {
		    for(int i = 0; i < n; i++) {
		        for(int j = i + 1; j < n; j++) { // Changed i to j
		            if(M[i][j] == 1) {
		                IloLinearNumExpr lin = model.linearNumExpr();
		                lin.addTerm(1, x[i]);
		                lin.addTerm(1, x[j]);
		                model.addLe(lin, 1); // Changed Le to addLe
		            }
		        }
		    }
		}

		private void creationObjective() throws IloException {
		IloLinearNumExpr fo =model.linearNumExpr();
			
		for(int i=0;i<n;i++) {
		
				fo.addTerm(x[i], 1);

				
			}
			model.addMaximize(fo); //MAX
		}

		
		
		public boolean solve() throws IloException {
	    	
	    	return model.solve();
	    }
	    public double [] getX() throws UnknownObjectException, IloException {
	    	double[] d= new double[n];
	    	
	    	if (solve()) {d=model.getValues(x);
	    				}
			return d;
	    	
	    	
	    }
	    public void getMaxStable() throws UnknownObjectException, IloException {
	    	
	    	double [] d= getX();
	    	System.out.println("S={");
	        
	    	for(int i=0;i<d.length;i++) {
	    		if(d[i]==1)
	    			System.out.println("V " +(i+1)+" ");
	    		
	    	}
	    	System.out.println("}");
	        }
	
	public static void main(String[] args) throws IloException {
		 int [][] M = {{0,1,1,0,0,0,0,0},{1,0,0,0,0,1,0,0},{1,0,0,0,0,0,1,0},{0,0,0,0,1,0,0,1},{0,0,0,1,0,1,0,0},{0,1,0,0,1,0,1,0},{0,0,1,0,0,1,0,1},{0,0,0,1,0,0,1,0}};
		MaxStable M1 =new MaxStable(M);
		M1.getMaxStable();
	
	}
		
		
		
}
