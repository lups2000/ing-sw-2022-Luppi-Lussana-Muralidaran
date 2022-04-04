package it.polimi.ingsw.Model;

public class StudentBag {
    private int numStudents;
    private int[] students;

    /**
     * constructor
     * the studentBag at the start of the game contains all the students, 26 per color
     */
    public StudentBag(){
        this.numStudents = 130;
        this.students = new int[5];
        for(int i=0;i<5;i++){
            this.students[i] = 26;
        }
    }

    public int[] getStudents() {
        return students;
    }

    public int getNumStudents() {
        return numStudents;
    }

    /**
     * method to add students to the bag
     * @param added is the array that counts, for each color, how many students need to be added to the studentBag
     */
    public void addStudents(int[] added){
        for(int i=0;i<5;i++){
            students[i] = students[i] + added[i];
            numStudents = numStudents + added[i];
        }
    }

    /**
     * method to extract random students from the studentBag
     * @param x is the number of students to extract
     * @return the array extracted which counts, for each color, how many students are extracted
     */
    public int[] drawStudent(int x){
        int[] extracted = new int[5];
        numStudents = numStudents + x;
        //modo per comporre il vettore extracted con funzioni random, da iterare x volte
        return extracted;
    }
}
