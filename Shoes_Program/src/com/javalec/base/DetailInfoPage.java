package com.javalec.base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.javalec.function.Dao_wdh;
import com.javalec.function.Dto_wdh;
import com.javalec.function.ShareVar;
import com.javalec.function.ShareVar_wdh;
import com.javalec.product.MainView_Info;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.event.PopupMenuEvent;

public class DetailInfoPage extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnBucket;
	private JLabel lblId;
	private JLabel lblImage;
	private JButton btnBucket2;
	private JButton btnBuy;
	private JLabel lblNewLabel_2;
	private JTextField tfName;
	private JLabel lblNewLabel_2_1;
	private JComboBox cbQty;
	private JLabel lblNewLabel_2_1_1;
	private JComboBox cbColor;
	private JLabel lblNewLabel_2_1_2;
	private JComboBox cbSize;
	private JLabel lblNewLabel_2_1_2_1;
	private JTextField tfPrice;
	private JComboBox cbEx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DetailInfoPage dialog = new DetailInfoPage();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DetailInfoPage() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				activatedScreen();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				deactivatedScreen();
			}
		});
		setBounds(ShareVar.position_window_x, ShareVar.position_window_y, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(ShareVar.RGB_red, ShareVar.RGB_green, ShareVar.RGB_blue));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getBtnBucket());
		{
			JButton btnCustomerUpdate = new JButton("New button");
			btnCustomerUpdate.setBounds(702, 10, 30, 30);
			contentPanel.add(btnCustomerUpdate);
		}
		{
			JButton btnMain = new JButton("메인");
			btnMain.setFont(new Font("굴림", Font.PLAIN, 12));
			btnMain.setBounds(660, 10, 30, 30);
			contentPanel.add(btnMain);
		}
		contentPanel.add(getLblId());
		contentPanel.add(getLblImage());
		contentPanel.add(getBtnBucket2());
		contentPanel.add(getBtnBuy());
		contentPanel.add(getLblNewLabel_2());
		contentPanel.add(getTfName());
		contentPanel.add(getLblNewLabel_2_1());
		contentPanel.add(getCbQty());
		contentPanel.add(getLblNewLabel_2_1_1());
		contentPanel.add(getCbColor());
		contentPanel.add(getLblNewLabel_2_1_2());
		contentPanel.add(getCbSize());
		contentPanel.add(getLblNewLabel_2_1_2_1());
		contentPanel.add(getTfPrice());
	}

	private JButton getBtnBucket() {
		if (btnBucket == null) {
			btnBucket = new JButton("New button");
			btnBucket.setBounds(744, 10, 30, 30);
		}
		return btnBucket;
	}

	private JLabel getLblId() {
		if (lblId == null) {
			lblId = new JLabel("");
			lblId.setHorizontalAlignment(SwingConstants.TRAILING);
			lblId.setBounds(457, 10, 191, 30);
			lblId.setText(ShareVar.userID);
		}
		return lblId;
	}

	private JLabel getLblImage() {
		if (lblImage == null) {
			lblImage = new JLabel("");
			lblImage.setIcon(new ImageIcon(DetailInfoPage.class.getResource("/com/javalec/image/PW.png")));
			lblImage.setBackground(new Color(255, 255, 255));
			lblImage.setBounds(45, 35, 400, 400);
		}
		return lblImage;
	}

	private JButton getBtnBucket2() {
		if (btnBucket2 == null) {
			btnBucket2 = new JButton("장바구니");
			btnBucket2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					bucketClick();

				}
			});
			btnBucket2.setBounds(150, 465, 200, 50);
		}
		return btnBucket2;
	}

	private JButton getBtnBuy() {
		if (btnBuy == null) {
			btnBuy = new JButton("바로구매");
			btnBuy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buyClick();
				}
			});
			btnBuy.setBounds(450, 465, 200, 50);
		}
		return btnBuy;
	}

	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("제품명 :");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNewLabel_2.setBounds(424, 100, 100, 30);
		}
		return lblNewLabel_2;
	}

	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setEditable(false);
			tfName.setBounds(536, 101, 112, 30);
			tfName.setColumns(10);
		}
		return tfName;
	}

	private JLabel getLblNewLabel_2_1() {
		if (lblNewLabel_2_1 == null) {
			lblNewLabel_2_1 = new JLabel("수량 :");
			lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNewLabel_2_1.setBounds(424, 280, 100, 30);
		}
		return lblNewLabel_2_1;
	}

	private JComboBox getCbQty() {
		if (cbQty == null) {
			cbQty = new JComboBox();
			cbQty.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			cbQty.setBounds(536, 283, 61, 25);
		}
		return cbQty;
	}

	private JLabel getLblNewLabel_2_1_1() {
		if (lblNewLabel_2_1_1 == null) {
			lblNewLabel_2_1_1 = new JLabel("색상 :");
			lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNewLabel_2_1_1.setBounds(424, 160, 100, 30);
		}
		return lblNewLabel_2_1_1;
	}

	private JComboBox getCbColor() {
		if (cbColor == null) {
			cbColor = new JComboBox();
			cbColor.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
				}

				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					colorChange();
				}

				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				}
			});
			cbColor.setBounds(536, 163, 94, 25);
		}
		return cbColor;
	}

	private JLabel getLblNewLabel_2_1_2() {
		if (lblNewLabel_2_1_2 == null) {
			lblNewLabel_2_1_2 = new JLabel("사이즈 :");
			lblNewLabel_2_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNewLabel_2_1_2.setBounds(424, 220, 100, 30);
		}
		return lblNewLabel_2_1_2;
	}

	private JComboBox getCbSize() {
		if (cbSize == null) {
			cbSize = new JComboBox();
			cbSize.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
				}

				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					sizeChange();
				}

				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				}
			});
			cbSize.setBounds(536, 223, 94, 25);
		}
		return cbSize;
	}

	private JLabel getLblNewLabel_2_1_2_1() {
		if (lblNewLabel_2_1_2_1 == null) {
			lblNewLabel_2_1_2_1 = new JLabel("가격 :");
			lblNewLabel_2_1_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
			lblNewLabel_2_1_2_1.setBounds(424, 340, 100, 30);
		}
		return lblNewLabel_2_1_2_1;
	}

	private JTextField getTfPrice() {
		if (tfPrice == null) {
			tfPrice = new JTextField();
			tfPrice.setEditable(false);
			tfPrice.setBounds(536, 341, 112, 30);
			tfPrice.setColumns(10);
		}
		return tfPrice;
	}

	// --- Method ---

	// 화면이 activated 되었을 때
	private void activatedScreen() {

		int seq = MainView_Info.clickSeq; // seq라는 숫자의 데이터 값 = MainView_Info에서 clickSeq(제품번호)라는 static int를 가져옴
		Dao_wdh dao_wdh = new Dao_wdh(seq); // Dao에 seq를 보냄
//		System.out.println(dao_wdh.viewDetailInfo());	// 잘 가져오는지 실험
		Dto_wdh dto_wdh = dao_wdh.viewDetailInfo();
//		System.out.println(dto_wdh.getPname());			// 잘 출력하는지 실험

		tfName.setText(dto_wdh.getPname()); // 제품명 출력
		cbQtyNum(); // 수량 넣기
		cbColorColumn(); // 색깔 넣기
		cbColor.setSelectedItem(dto_wdh.getPcolor());
		cbSizeColumn(); // 사이즈 넣기
		cbSize.setSelectedItem(dto_wdh.getPsize());
		tfPrice.setText(Integer.toString(dto_wdh.getPprice())); // 제품 가격 출력

		// Image File
		String filePath = Integer.toString(ShareVar_wdh.image);

		lblImage.setIcon(new ImageIcon(filePath));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);

		File file = new File(filePath);
		file.delete();

	}

	// 화면이 deactivated 되었을 때
	private void deactivatedScreen() {

		tfName.setText("");
		cbQty.removeAllItems();
		cbColor.removeAllItems();
		cbSize.removeAllItems();
		tfPrice.setText("");
	}

	// cbQty의 재고수량 만들기
	private void cbQtyNum() {
		int seq = MainView_Info.clickSeq; // seq라는 숫자의 데이터 값 = MainView_Info에서 clickSeq(제품번호)라는 static int를 가져옴
		Dao_wdh dao_wdh = new Dao_wdh(seq); // Dao에 seq를 보냄
		for (int i = 0; i <= dao_wdh.viewDetailInfo().getPqty(); i++) {
			cbQty.addItem(i);
		}
	}

	// 현재 재고가 몇개인지 나타내는 function
	private int currentStock() {
		int seq = searchPseq(); // seq라는 숫자의 데이터 값 = MainView_Info에서 clickSeq(제품번호)라는 static int를 가져옴
		Dao_wdh dao_wdh = new Dao_wdh(seq); // Dao에 seq를 보냄
		Dto_wdh dto_wdh = dao_wdh.viewDetailInfo();
		int cStock = dto_wdh.getPqty();
		return cStock;
	}

	// 상품의 color를 나타내는 combobox의 function
	private void cbColorColumn() {
		int seq = MainView_Info.clickSeq; // seq라는 숫자의 데이터 값 = MainView_Info에서 clickSeq(제품번호)라는 static int를 가져옴
		Dao_wdh dao_wdh = new Dao_wdh(seq); // Dao에 seq를 보냄
//		System.out.println(dao_wdh.productColor());	// ArrayList 잘 되었는지 확인
		for (int i = 0; i < dao_wdh.productColor().size(); i++) {
			cbColor.addItem(dao_wdh.productColor().get(i));
		}
	}

	// 상품의 size를 나타내는 combobox의 function
	private void cbSizeColumn() {
		int seq = MainView_Info.clickSeq; // seq라는 숫자의 데이터 값 = MainView_Info에서 clickSeq(제품번호)라는 static int를 가져옴
		Dao_wdh dao_wdh = new Dao_wdh(seq); // Dao에 seq를 보냄
//		System.out.println(dao_wdh.productSize());	// ArrayList 잘 되었는지 확인
		for (int i = 0; i < dao_wdh.productSize().size(); i++) {
			cbSize.addItem(dao_wdh.productSize().get(i));
		}
	}

	// cbColor의 색상을 바꾸면 제품명, 색상, 사이즈를 통해 제품코드를 search 한 후, 제품코드를 통해 나머지 정보를 다시
	// search
	private void colorChange() {
		cbSize.removeAllItems();
		cbQty.removeAllItems();

		searchSizeCb();
		Dao_wdh dao_wdh = new Dao_wdh(searchPseq()); // Dao에 seq를 보냄
		Dto_wdh dto_wdh = dao_wdh.viewDetailInfo();

		for (int i = 0; i <= dao_wdh.viewDetailInfo().getPqty(); i++) { // 수량 콤보박스를 만듬
			cbQty.addItem(i);
		}

		tfPrice.setText(Integer.toString(dto_wdh.getPprice())); // 제품 가격 출력

		// Image File
		String filePath = Integer.toString(ShareVar_wdh.image);

		lblImage.setIcon(new ImageIcon(filePath));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);

		File file = new File(filePath);
		file.delete();
	}

	// cbSize의 색상을 바꾸면 제품명, 색상, 사이즈를 통해 제품코드를 search 한 후, 제품코드를 통해 나머지 정보를 다시 search
	private void sizeChange() {
		cbQty.removeAllItems();

		Dao_wdh dao_wdh = new Dao_wdh(searchPseq()); // Dao에 seq를 보냄
		Dto_wdh dto_wdh = dao_wdh.viewDetailInfo();

		for (int i = 0; i <= dao_wdh.viewDetailInfo().getPqty(); i++) { // 수량 콤보박스를 만듬
			cbQty.addItem(i);
		}

		tfPrice.setText(Integer.toString(dto_wdh.getPprice())); // 제품 가격 출력

		// Image File
		String filePath = Integer.toString(ShareVar_wdh.image);

		lblImage.setIcon(new ImageIcon(filePath));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);

		File file = new File(filePath);
		file.delete();

	}

	// 제품명, 색상, 사이즈를 통해 제품코드를 search
	private int searchPseq() {
		String pName = tfName.getText();
//		System.out.println(pName); // 이름 가져오기
		String pColor = cbColor.getSelectedItem().toString();
//		System.out.println(pColor); // 색깔 가져오기
		int pSize = Integer.parseInt(cbSize.getSelectedItem().toString());
//		System.out.println(pSize); // 사이즈 가져오기
		Dao_wdh dao_wdh = new Dao_wdh(pName, pColor, pSize);
//		System.out.println(dao_wdh.searchSeq());
		return dao_wdh.searchSeq();
	}

	// 바로구매 버튼을 눌렀을 때
	private void buyClick() {
		if (ShareVar.userID.equals("")) {
			JOptionPane.showMessageDialog(null, "로그인이 필요합니다.");
		} else if (cbQty.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "수량을 선택해주세요.");
		} else {
			int seq = searchPseq(); // seq라는 숫자의 데이터 값 = MainView_Info에서 clickSeq(제품번호)라는 static int를 가져옴
			String num = cbQty.getSelectedItem().toString();
			int stock = currentStock() - Integer.parseInt(num);
			Dao_wdh dao_wdh = new Dao_wdh(seq, stock);
			dao_wdh.updateAction();
			getWindow();
			deactivatedScreen();
			activatedScreen();
			JOptionPane.showMessageDialog(null, "구매를 완료하였습니다.");
			dispose(); // 화면 종료는 dispose();로 해주면 됨
			this.setVisible(false); // 화면 종료
			MainView window = new MainView(); // BuyList화면 켜줌
			window.setVisible(true);
		}
	}

	// 바로구매 눌렀을 때 창에서 가져올 것들
	private void getWindow() {
		int sp_seq = searchPseq(); // sp_seq
		String scustomer_id = lblId.getText(); // scustomer_id
		int sprice = Integer.parseInt(tfPrice.getText()); // sprice
		String sdate = LocalDate.now().toString(); // sdate
		int sqty = Integer.parseInt(cbQty.getSelectedItem().toString()); // sqty

		Dao_wdh dao_wdh = new Dao_wdh(sp_seq, scustomer_id, sdate, sprice, sqty);
		dao_wdh.saleInsertAction();

	}

	// 로그인이 되어 있는지, 수량이 0개인지 확인 후 아니라면 장바구니 버튼을 눌렀을 때 다이얼로그를 띄워서 장바구니 페이지로 갈지 안갈지 확인
	private void bucketClick() {

		if (ShareVar.userID.equals("")) {
			JOptionPane.showMessageDialog(null, "로그인이 필요합니다."); // 로그인이 되어있지 않다면 구매 불가
		} else if (cbQty.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "수량을 선택해주세요.");
		} else {
			compareAndInsertOrUpdate();
			int result = JOptionPane.showConfirmDialog(null, "장바구니로 이동할까요?", "", JOptionPane.YES_NO_OPTION);
			// 로그인이 되어 있다면 장바구니 메세지 출력
			if (result == JOptionPane.CLOSED_OPTION) {
			} else if (result == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, "상품을 장바구니에 저장하였습니다"); // 메세지 출력
				dispose(); // 화면 종료는 dispose();로 해주면 됨
				this.setVisible(false); // 화면 종료
				BuyList window = new BuyList(); // BuyList화면 켜줌
				window.setVisible(true);
			} else {
			}
		}
	}

	// 장바구니 버튼을 눌렀을 때 현재 페이지의 p_seq가 장바구니 품목에 있는 p_seq와 겹칠경우 Update, 없을경우 insert
	private void compareAndInsertOrUpdate() {
		Dao_wdh dao_wdh = new Dao_wdh();
		boolean containP_seq = dao_wdh.searchP_seqFromSave().contains(searchPseq());
		if (containP_seq) {
			saveUpdate();
		} else {
			saveInsert();
		}
	}

	// seq값을 비교해서 임시저장 Entity에 넣어 줌
	private void saveInsert() {
		int sap_seq = searchPseq(); // seq라는 숫자의 데이터 값 = MainView_Info에서 clickSeq(제품번호)라는 static int를 가져옴
		String sapcustomer_id = lblId.getText(); // id 가져옴
		int saveQty = (cbQty.getSelectedIndex());
		Dao_wdh dao_wdh = new Dao_wdh(sap_seq, sapcustomer_id, saveQty);
		dao_wdh.insertAction(); // 임시저장 Entity에 넣어줌
	}

	// seq값이 같을 경우 임시저장 Entity에 수량만 추가해줌
	private void saveUpdate() {
		int saveQty = cbQty.getSelectedIndex();
		System.out.println(cbQty.getSelectedIndex());
		int p_seq = searchPseq();
		Dao_wdh dao_wdh = new Dao_wdh(saveQty, p_seq);
		dao_wdh.searchP_seqAndUpdate();
	}

	// 제품명과 색깔을 통해 사이즈 콤보박스 생성
	private void searchSizeCb() {
		String pName = tfName.getText();
		String pColor = cbColor.getSelectedItem().toString();
		Dao_wdh dao_wdh = new Dao_wdh();
		dao_wdh.productSizeSearch(pName, pColor);
		for (int i = 0; i < dao_wdh.productSizeSearch(pName, pColor).size(); i++) {
			cbSize.addItem(dao_wdh.productSizeSearch(pName, pColor).get(i));
		}

	}

} // End
