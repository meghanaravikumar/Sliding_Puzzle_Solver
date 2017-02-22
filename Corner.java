/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Corner {
    
    public final int x;
    public final int y;
    
    public Corner(int xcoord, int ycoord){
        x = xcoord;
        y = ycoord;
    }
    
    @Override
    public String toString(){
    	return new String(x + " " + y);
    }

    @Override
    public int hashCode() {
        return 47 * (47 * 3 + this.x) + this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Corner that = (Corner) obj;
        return this.y == that.y && this.x == that.x;
    }
    
    public Corner copy(){
    	return new Corner(this.x, this.y);
    }
    
}
