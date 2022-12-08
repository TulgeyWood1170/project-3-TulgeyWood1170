/**
 * Project 3
 * File i/o, parsing data, setting and getting student data
 * Printing student Array and sorted student Array
 * Lisa Roland
 * 12.07.2022
 */

public class Student {

    public static final int TOTAL_ASSIGNMENTS = 11;
    public static final int TOTAL_EXAMS = 5;
    
    private String firstName;
    private String lastName;

    private int examScores[] = new int[5];
    // examScores[0] = exam1Score
    // examScores[1] = exam2Score
    // examScores[2] = exam3Score
    // examScores[3] = writtenFinalScore
    // examScores[4] = practicalFinalScore
    
    private int assignmentScores[] = new int[TOTAL_ASSIGNMENTS];

    // set methods are called by importStudentData and GradebookApp 
    public void setFirstName(String fName) { 
        firstName = fName;
    }
    
    // get methods are used by both gradebook and student
    public String getFirstName() { 
        return firstName; 
    }

    public void setLastName(String lName) { 
        lastName = lName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setExam1Score(int exam1Score) { 
        if (exam1Score < 0 || exam1Score > 100) {
            throw new IllegalArgumentException("valid scores are 0-100");
        }
        examScores[0] = exam1Score;
    }
    
    public int getExam1Score() {
        return examScores[0];
    }

    public void setExam2Score(int exam2Score) { 
        if (exam2Score < 0 || exam2Score > 100) {
            throw new IllegalArgumentException("valid scores are 0-100");
        }
        examScores[1] = exam2Score;
    }

    public int getExam2Score() {
        return examScores[1];
    }    

    public void setExam3Score(int exam3Score) { 
        if (exam3Score < 0 || exam3Score > 100) {
            throw new IllegalArgumentException("valid scores are 0-100");
        }
        examScores[2] = exam3Score;
    }

    public int getExam3Score() {
        return examScores[2];
    }

    public void setWrittenFinalScore(int writtenFinalScore) { 
        if (writtenFinalScore < 0 || writtenFinalScore > 100) {
            throw new IllegalArgumentException("valid scores are 0-100");
        }
        examScores[3] = writtenFinalScore;        
    }

    public int getWrittenFinalScore() {
        return examScores[3];        
    }

    public void setPracticalFinalScore(int practicalFinalScore) { 
        if (practicalFinalScore < 0 || practicalFinalScore > 100) {
            throw new IllegalArgumentException("valid scores are 0-100");
        }
        examScores[4] = practicalFinalScore;
    }

    public int getPracticalFinalScore() {
        return examScores[4];
    }

    // returns exam average to main rounded to 1 decimal place
    public double getExamAverage() {
        int lowestScore = examScores[0];
        int sum = 0;
        double avg = 0.0;

        // find lowest exam score
        for (int i = 1; i < examScores.length; i++) {
            if (examScores[i] < lowestScore)
                lowestScore = examScores[i];
        }        
        // sum exam scores and remove lowest score
        for (int i = 0; i < examScores.length; i++) {
            sum = sum + examScores[i];
        }    
        sum = sum - lowestScore;
        avg = sum / (double)(examScores.length - 1);

        return (Math.round(avg * 10) / 10.0);
    }

    public void setAssignmentScore(int assignmentNumber, int score) { 

        // check valid assignment # (1 - TOTAL_ASSIGNMENTS) else IllegalArgumentException
        if (assignmentNumber < 1 || assignmentNumber > TOTAL_ASSIGNMENTS) {
            throw new IllegalArgumentException("assignment numbers are 1-" + Student.TOTAL_ASSIGNMENTS);
        }

        //check valid score else IllegalArgumentException
        if ((score < 0) || (score > 100)) {
            throw new IllegalArgumentException("valid scores are 0-100");
        }

        assignmentScores[assignmentNumber-1] = score;
    }

    public int getAssignmentScore(int i) {
        if (i < 1 || i > TOTAL_ASSIGNMENTS) {
            throw new IllegalArgumentException("assignment numbers are 1-" + TOTAL_ASSIGNMENTS);
        }
        
        return assignmentScores[i-1];
    }

    public double getAssignmentAverage() {
        double lowestScore = assignmentScores[0];
        double sum = 0.0;
        double avg = 0.0;        

        for (int i = 1; i < assignmentScores.length; i++) {
            if (assignmentScores[i] < lowestScore)
                lowestScore = assignmentScores[i];
        }

        for (int i = 0; i < assignmentScores.length; i++) {
            sum = sum + assignmentScores[i];
        }
        sum = sum - lowestScore;
        avg = sum / (double)(assignmentScores.length - 1);

        return (Math.round(avg * 10) / 10.0);
    }

    public double getGradeAverage() {
        int lowestScoreExam = examScores[0];
        int lowestScoreAssignment = assignmentScores[0]; 
        int sum = 0;
        double avg = 0.0;
        // find lowest exam score
        for (int i = 1; i < examScores.length; i++) {
            if (examScores[i] < lowestScoreExam)
                lowestScoreExam = examScores[i];
        }        
        // sum exam scores and remove lowest score
        for (int i = 0; i < examScores.length; i++) {
            sum = sum + examScores[i];
        }    
        sum = sum - lowestScoreExam;

        //find lowest assignment score
        lowestScoreAssignment = assignmentScores[0];
        for (int i = 1; i < assignmentScores.length; i++) {
            if (assignmentScores[i] < lowestScoreAssignment)
                lowestScoreAssignment = assignmentScores[i];
        }

        for (int i = 0; i < assignmentScores.length; i++) {
            sum = sum + assignmentScores[i];
        }
        sum = sum - lowestScoreAssignment;

        avg = sum / (double)(examScores.length - 1 + assignmentScores.length - 1);

        return (Math.round(avg * 10) / 10.0);
    }

    // parse 1 line from input file to 1 student's data
    public void importStudentData(String record) {
        String studentData[] = record.split(",");
        if (studentData.length > (2 + examScores.length + assignmentScores.length)) {
            System.out.println("The first bad student record is: ");
            System.out.println(record);
            throw new IllegalArgumentException("Too much data in record string read from input file.");
        }
        setFirstName(studentData[0]);
        setLastName(studentData[1]);
        setExam1Score(Integer.parseInt(studentData[2]));
        setExam2Score(Integer.parseInt(studentData[3]));
        setExam3Score(Integer.parseInt(studentData[4]));
        setWrittenFinalScore(Integer.parseInt(studentData[5]));
        setPracticalFinalScore(Integer.parseInt(studentData[6]));

        for (int i = 0; i < assignmentScores.length; i++) {
            setAssignmentScore(i+1, Integer.parseInt(studentData[i+7]));    
        }
    }

    public String exportStudentData() {
        String outData = getFirstName() + "," + getLastName() + "," + 
            getExam1Score() + "," + getExam2Score() + "," + getExam3Score() + "," +
            getWrittenFinalScore() + "," + getPracticalFinalScore();
        for (int i = 0; i < assignmentScores.length; i++) {
            outData = outData + "," + getAssignmentScore(i+1);
        }

        return outData;
    }
    
    public static int getDataColumnLength() {
        return (2 + TOTAL_EXAMS + TOTAL_ASSIGNMENTS);
    }    
}