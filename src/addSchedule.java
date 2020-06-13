//suminaddschedule
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class addSchedule extends JFrame {
	Container c;
	JTextField tf1, tf2, tf3;
	JComboBox<String> strCombo1, strCombo2, strCombo3, strCombo4;
	JTextArea ta;
	JButton btn1, btn2;
	int index = 1;
	
	private String[] where = { "사내 회의실", "회사 앞 카페", "로비", "기타" };
	private String[] office = { "A회사", "B회사", "C회사", "기타" };
	private String[] kinds = { "회의", "계약", "워크샵", "행사", "기타" };
	private String[] who = { "박OO 차장", "김OO 팀장", "이OO 사장" ,"그 외"};

	addSchedule() {
		setTitle("addSchedule");
		c = getContentPane();
		c.setLayout(null);

		JLabel la1 = new JLabel("제목");
		la1.setSize(50, 20);
		la1.setLocation(40, 30);
		c.add(la1);
		tf1 = new JTextField(30);
		tf1.setSize(260, 20);
		tf1.setLocation(80, 30);
		c.add(tf1);

		JLabel la2 = new JLabel("장소");
		la2.setSize(50, 20);
		la2.setLocation(40, 60);
		c.add(la2);
		strCombo1 = new JComboBox<String>(where);
		strCombo1.setSize(100, 20);
		strCombo1.setLocation(80, 60);
		c.add(strCombo1);

		JLabel la3 = new JLabel("기관");
		la3.setSize(50, 20);
		la3.setLocation(200, 60);
		c.add(la3);
		strCombo2 = new JComboBox<String>(office);
		strCombo2.setSize(100, 20);
		strCombo2.setLocation(240, 60);
		c.add(strCombo2);

		JLabel la4 = new JLabel("분류");
		la4.setSize(50, 20);
		la4.setLocation(40, 90);
		c.add(la4);
		strCombo3 = new JComboBox<String>(kinds);
		strCombo3.setSize(100, 20);
		strCombo3.setLocation(80, 90);
		c.add(strCombo3);

		JLabel la5 = new JLabel("대상");
		la5.setSize(50, 20);
		la5.setLocation(200, 90);
		c.add(la5);
		strCombo4 = new JComboBox<String>(who);
		strCombo4.setSize(100, 20);
		strCombo4.setLocation(240, 90);
		c.add(strCombo4);

		JLabel la6 = new JLabel("시각");
		la6.setSize(50, 20);
		la6.setLocation(40, 120);
		c.add(la6);
		tf2 = new JTextField(10);
		tf2.setSize(100, 20);
		tf2.setLocation(80, 120);
		c.add(tf2);
		
		JLabel la7 = new JLabel("시간");
		la7.setSize(50, 20);
		la7.setLocation(200, 120);
		c.add(la7);
		tf3 = new JTextField(10);
		tf3.setSize(100, 20);
		tf3.setLocation(240, 120);
		c.add(tf3);

		JLabel la8 = new JLabel("내용");
		la8.setSize(50, 20);
		la8.setLocation(40, 150);
		c.add(la8);
		ta = new JTextArea(7, 40);
		ta.setSize(300, 100);
		ta.setLocation(40, 180);
		c.add(ta);

		btn1 = new JButton("저장");
		btn1.setSize(70, 30);
		btn1.setLocation(120, 300);
		btn1.addActionListener(new MyActionListener());
		c.add(btn1);

		btn2 = new JButton("취소");
		btn2.setSize(70, 30);
		btn2.setLocation(200, 300);
		btn2.addActionListener(new MyActionListener());
		c.add(btn2);

		setSize(400, 400);
		setVisible(true);

	}

private class MyActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn2) {
			addSchedule.this.dispose(); //취소버튼 클릭시 창 끄기
		} else if(e.getSource() == btn1) {
			try {
//				File f = new File("schedule/" + CalendarSchedule.year + 
//						CalendarSchedule.month + "-" + CalendarSchedule.day + "#" + index + ".txt");
//				
//				if(f.isFile()) {
//					index++;
//					f = new File("schedule/" + CalendarSchedule.year + 
//							CalendarSchedule.month + "-" + CalendarSchedule.day + "-" + index + ".txt");
//				}
//				
//				
//					FileWriter fw = new FileWriter(f);
//					BufferedWriter bw = new BufferedWriter(fw);

				FileWriter fw = new FileWriter("schedule/" + CalendarSchedule.year + CalendarSchedule.month + "-"+ CalendarSchedule.day + ".txt"); 
				//저장파일이름설정.
				BufferedWriter bw = new BufferedWriter(fw);

				//System.out.println(tf1.getText());

				String str = tf1.getText()+"\r\n";
				str += strCombo1.getSelectedItem()+"\r\n";
				str += strCombo2.getSelectedItem()+"\r\n";
				str += strCombo3.getSelectedItem()+"\r\n";
				str += strCombo4.getSelectedItem()+"\r\n";
				str += tf2.getText()+"\r\n";
				str += tf3.getText()+"\r\n";
				str += ta.getText()+"\r\n";
				
				Color col = new Color((int)(Math.random()*256), (int)(Math.random()*256),
			               (int)(Math.random()*256));
				
				str += col.getRed() + "\r\n";
				str += col.getBlue() + "\r\n";
				str += col.getGreen() + "\r\n";
				bw.write(str);
				bw.newLine(); // 줄바꿈
				bw.close();
				
				addSchedule.this.dispose(); //스케줄 저장 후 창 끄기
				GetFile g = new GetFile(SwingCalendar.schedule, SwingCalendar.mywork);
			} catch (IOException ex) {
				System.err.println(ex); // 에러가 있다면 메시지 출력
				System.exit(1);
			}
		}
	}
}
}