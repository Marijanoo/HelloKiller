package HelloKiller;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class HelloKiller implements ActionListener, KeyListener {

	public static void main(String[] args) { 
		new HelloKiller();
	}

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // screen size

	JLabel menu = new JLabel(); // bottom menu

	JFrame frame = new JFrame(); // frame

	int frameX = 50; // ku ja nis bari
	int frameY = 50;

	// entities
	JLabel playerOne = new JLabel();
	JLabel playerTwo = new JLabel();
	JLabel clone = new JLabel();

	// skills edhe players list
	ArrayList<JLabel> skills = new ArrayList<>();
	ArrayList<JLabel> players = new ArrayList<>();

	// fruit lists
	ArrayList<JButton> apples = new ArrayList<>();
	ArrayList<JButton> oranges = new ArrayList<>();
	ArrayList<JButton> lemons = new ArrayList<>();

	// map stuff
	JLabel[][] grass = new JLabel[10][10];
	int[][] grassMap = new int[10][10];
	JLabel[][] fences = new JLabel[12][11];

	// layers per mi qit items correctly
	JLayeredPane layer = new JLayeredPane();

	JLabel jumpscare = new JLabel();

	// heal / dmg timers
	Timer removeKittyHp = new Timer(750, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (players.contains(playerTwo)) {
				if (hp >= 5) {
					hp -= 5;
					if (hp == 0 && players.contains(playerOne)) {
						removeKittyHp.stop();
						disableMovement();
						playerOne.setVisible(false);
						JOptionPane.showMessageDialog(frame, "Hello Kitty has died!");
						layer.remove(playerOne);
						frame.remove(playerOne);
						players.remove(playerOne);
					}
				}
			}
		}
	});

	Timer removeKillerHp = new Timer(750, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (players.contains(playerOne)) {
				if (hp2 >= 5) {
					hp2 -= 5;
					if (hp2 == 0 && players.contains(playerTwo)) {
						removeKillerHp.stop();
						disableMovement();
						playerTwo.setVisible(false);
						JOptionPane.showMessageDialog(frame, "Murder Kitty has died!");
						layer.remove(playerTwo);
						frame.remove(playerTwo);
						players.remove(playerTwo);
					}

				}
			}
		}

	});;

	Timer heal = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (hp == 0) {

			} else if (hp >= 5) {
				if (hp == 100) {

				} else {
					hp += 5;
				}

			} else {

			}
		}
	});

	int playerOneID = 1; // used for skins (sides)
	int playerTwoID = 2; // same thing

	int skinID = 1; // changes the skin when collecting fruta

	boolean jumpscared = false; // jumpscare disabler
	// frame variables

	int frameWidth = 600;
	int frameHeight = 600;

	// entity width/height/count

	int playerWidth = 38;
	int playerHeight = 47;

	int appleWidth = 12;
	int appleHeight = 14;
	int appleCount = 100;

	int orangeWidth = 26;
	int orangeHeight = 25;
	int orangeCount = 30;

	int lemonWidth = 23;
	int lemonHeight = 26;
	int lemonCount = 10;

	int grassSize = 50;

	// fruits and stuff

	boolean doneApples = false;
	boolean doneOranges = false;
	boolean doneLemons = false;
	boolean setFruit = false;

	// contact checker
	boolean touch = false;
	boolean cloneTouch = false;

	// movement thingies per diagonal edhe sides
	boolean up = false;
	boolean down = false;
	boolean left = false;
	boolean right = false;

	boolean up2 = false;
	boolean down2 = false;
	boolean left2 = false;
	boolean right2 = false;

	// superpower stuff

	int blastAway = 100; // blast super power distance
	int contactDmg = 10; // contact damage on touch

	boolean usedPower = false; // invisibility super power
	boolean switched = false; // switching super power
	boolean mirroring = false; // mirror the clone

	boolean exploded = false; // explosion super power
	boolean blasted = false; // pushing super power
	boolean healing = false; // healing super power

	// hello kitty variables
	int i = frameWidth / 2 - 60 - 30;
	int j = frameWidth / 2 - playerHeight - 25;
	int speed = 3;
	int hp = 100;

	// murder killer variables
	int i2 = frameWidth / 2 + 30 + 30;
	int j2 = frameWidth / 2 - playerHeight - 25;
	double speed2 = 3;
	double setspeed2 = 3;
	int hp2 = 100;

	// clone reserved variables
	int i3 = 0;
	int j3 = 0;

	// health bars
	JLabel healthBarKitty = new JLabel("HP: " + hp);
	JLabel healthBarKiller = new JLabel("HP: " + hp2);

	public HelloKiller() {

		frame.setVisible(true);
		frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icon.png")).getImage());
		frame.setSize(frameWidth + 16, frameHeight + 39);
		layer.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		layer.setDoubleBuffered(true);

		frame.setContentPane(layer);

		frame.setResizable(false);
		frame.setLayout(null);
		frame.setLocation(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.getContentPane().setBackground(Color.white);
		frame.setTitle("HelloKiller");

		layer.add(jumpscare, JLayeredPane.DRAG_LAYER);
		jumpscare.setBounds(0, 0, (int) frame.getSize().getWidth(), (int) frame.getSize().getHeight());

		jumpscare.setVisible(false);
		jumpscare.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jumpscare.png")));

		setMenu();
		setGrass();
		setApples();
		setSkills();

//		JOptionPane.showMessageDialog(frame, "Welcome to Hello Kitty Slave Simulator");
//		JOptionPane.showMessageDialog(frame, "Hello Kitty's goal: collect all the fruits without dying");
//		JOptionPane.showMessageDialog(frame, "Murder Kitty's goal: kill Hello Kitty before they grab all the fruits");
//		JOptionPane.showMessageDialog(frame, "Floor tiles: If either Kitty steps on the other Kitty's grass, they will take 5 damage per 3/4ths of a second and become slower");
//		JOptionPane.showMessageDialog(frame, "You can view the health bars in the corners");
//		JOptionPane.showMessageDialog(frame, "Hello Kitty's SuperPowers: (Button 'C') pushes Murder Kitty 2.5 blocks away, "
//				+ "(Button 'V'): makes 1-3 explosions randomly in the map, clearing the grass");
//		JOptionPane.showMessageDialog(frame, "Murder Kitty's SuperPowers: (Button 'I') Murder Kitty becomes invisible for 5 seconds (immune to dmg & slowed), "
//				+ "If Murder Kitty makes contact with Hello Kitty they will deal 25 damage (will become a skill), Hello Kitty gains immunity to damage from contact for 5 seconds");
//		JOptionPane.showMessageDialog(frame, "Good Luck!");

		setPlayers();
		convertGrassBlack();
		convertGrassPink();
		refreshRate();

	}

	public void setSkills() {
		for (int i = 0; i < 6; i++) {
			skills.add(new JLabel());
			if (i >= 3) {
				skills.get(i).setBounds(71 + 36 * i + 24 * i + 35 + frameX, 500 + 7 + 6 + frameY, 24, 24);
			} else {
				skills.get(i).setBounds(71 + 36 * i + 24 * i + frameX, 500 + 7 + 6 + frameY, 24, 24);
			}
			layer.add(skills.get(i), JLayeredPane.POPUP_LAYER);

		}

		skills.get(0).setIcon(new ImageIcon(getClass().getClassLoader().getResource("allmightypush.png")));
		skills.get(1).setIcon(new ImageIcon(getClass().getClassLoader().getResource("bomb.png")));
		skills.get(2).setIcon(new ImageIcon(getClass().getClassLoader().getResource("heal.png")));
		skills.get(3).setIcon(new ImageIcon(getClass().getClassLoader().getResource("clone.png")));
		skills.get(4).setIcon(new ImageIcon(getClass().getClassLoader().getResource("swap.png")));
		skills.get(5).setIcon(new ImageIcon(getClass().getClassLoader().getResource("invis.png")));

	}

	public void mirror() {

		mirroring = true;
		skills.get(3).setVisible(false);

		i3 = frameWidth - (i2 + playerWidth);
		j3 = frameHeight - (j2 + playerHeight * 5 / 3);

		players.add(clone);
		clone.setBounds(i3, j3, playerWidth, playerHeight);
		clone.setVisible(true);
		clone.setIcon(new ImageIcon(getClass().getClassLoader().getResource("down2.png")));
		layer.add(clone, JLayeredPane.DRAG_LAYER);

		Timer reset = new Timer(30000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mirroring = false;
				skills.get(3).setVisible(true);
			}

		});

		Timer hold = new Timer(2000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reset.setRepeats(false);
				reset.start();
				players.remove(clone);
				clone.setVisible(false);
				layer.remove(clone);
				frame.remove(clone);
				i3 = 0;
				j3 = 0;
			}
		});
		hold.setRepeats(false);
		hold.start();

	}

	public void swap() {

		switched = true;
		skills.get(4).setVisible(false);

		int tempX = i;
		int tempY = j;

		i = i2;
		j = j2;

		i2 = tempX;
		j2 = tempY;

		Timer reset = new Timer(15000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				skills.get(4).setVisible(true);
				switched = false;
			}
		});
		reset.setRepeats(false);
		reset.start();

	}

	public void heal() {
		healing = true;
		skills.get(2).setVisible(false);

		if (hp != 100) {
			hp += 5;
		}

		heal.setRepeats(true);
		heal.start();

		Timer stopHeal = new Timer(10000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				heal.setRepeats(false);
				heal.stop();
			}

		});
		stopHeal.setRepeats(false);
		stopHeal.start();

		Timer reset = new Timer(30000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				healing = false;
				skills.get(2).setVisible(true);
			}
		});
		reset.setRepeats(false);
		reset.start();
	}

	public void blast() {

		skills.get(0).setVisible(false);
		blasted = true;
		JLabel difference = new JLabel();
		difference.setBounds(playerOne.getX() - playerTwo.getX(), playerOne.getY() - playerTwo.getY(), playerWidth, playerHeight);

		if (playerTwo.getX() > playerOne.getX() - (playerWidth / 2) && playerTwo.getX() + playerWidth < playerOne.getX() + ((playerWidth * 3) / 2)
				&& playerTwo.getY() < playerOne.getY()) {

			if (playerTwo.getY() - blastAway >= 0 + playerWidth) { // top mid sends u up
				j2 = playerTwo.getY() - blastAway;
			} else {
				j2 = 50;
			}

		} else if (playerTwo.getX() > playerOne.getX() - (playerWidth / 2) && playerTwo.getX() < playerOne.getX() + ((playerWidth * 3) / 2)
				&& playerTwo.getY() > playerOne.getY()) {

			if (playerTwo.getY() + blastAway <= 550 - playerHeight - 16) { // bottom mid sends u down
				j2 = playerTwo.getY() + blastAway;
			} else {
				j2 = 550 - playerHeight;
			}

		} else if (playerTwo.getY() > playerOne.getY() - (playerHeight / 2) && playerTwo.getY() < playerOne.getY() + ((playerHeight * 3) / 2)
				&& playerTwo.getX() < playerOne.getX()) {

			if (playerTwo.getX() - blastAway >= playerWidth) {
				i2 = playerTwo.getX() - blastAway + 16;
			} else {
				i2 = 50;
			}

		} else if (playerTwo.getX() > playerOne.getX() - (playerHeight / 2) && playerTwo.getX() < playerOne.getX() + ((playerHeight * 3) / 2)
				&& playerTwo.getY() > playerOne.getY()) {

			if (playerTwo.getX() + blastAway <= 550 - playerWidth) {
				i2 = playerTwo.getX() + blastAway;
			} else {
				i2 = 550 - playerWidth;
			}

		} else {

			if (difference.getX() > 0) {
				if (playerTwo.getX() - blastAway >= playerWidth) {
					i2 = playerTwo.getX() - blastAway + 16;
				} else {
					i2 = 50;
				}
			}

			if (difference.getX() < 0) {
				if (playerTwo.getX() + blastAway <= 550 - playerWidth) {
					i2 = playerTwo.getX() + blastAway;
				} else {
					i2 = 550 - playerWidth;
				}
			}

			if (difference.getY() > 0) {
				if (playerTwo.getY() - blastAway >= 0 + playerHeight) {
					j2 = playerTwo.getY() - blastAway;
				} else {
					j2 = 50;
				}
			}

			if (difference.getY() < 0) {
				if (playerTwo.getY() + blastAway <= 550 - playerHeight - 16) {
					j2 = playerTwo.getY() + blastAway;
				} else {
					j2 = 550 - playerHeight;
				}
			}
		}

		if (players.contains(clone)) {
			if (clone.getX() > playerOne.getX() - (playerWidth / 2) && clone.getX() + playerWidth < playerOne.getX() + ((playerWidth * 3) / 2)
					&& clone.getY() < playerOne.getY()) {

				if (clone.getY() - blastAway >= 0 + playerWidth) { // top mid sends u up
					j3 = clone.getY() - blastAway;
				} else {
					j3 = 50;
				}

			} else if (clone.getX() > playerOne.getX() - (playerWidth / 2) && clone.getX() < playerOne.getX() + ((playerWidth * 3) / 2)
					&& clone.getY() > playerOne.getY()) {

				if (clone.getY() + blastAway <= 550 - playerHeight - 16) { // bottom mid sends u down
					j3 = clone.getY() + blastAway;
				} else {
					j3 = 550 - playerHeight;
				}

			} else if (clone.getY() > playerOne.getY() - (playerHeight / 2) && clone.getY() < playerOne.getY() + ((playerHeight * 3) / 2)
					&& clone.getX() < playerOne.getX()) {

				if (clone.getX() - blastAway >= playerWidth) {
					i3 = clone.getX() - blastAway + 16;
				} else {
					i3 = 50;
				}

			} else if (clone.getX() > playerOne.getX() - (playerHeight / 2) && clone.getX() < playerOne.getX() + ((playerHeight * 3) / 2)
					&& clone.getY() > playerOne.getY()) {

				if (clone.getX() + blastAway <= 550 - playerWidth) {
					i3 = clone.getX() + blastAway;
				} else {
					i3 = 550 - playerWidth;
				}

			} else {

				if (difference.getX() > 0) {
					if (clone.getX() + blastAway <= 550 - playerWidth) {
						i3 = clone.getX() + blastAway;
					} else {
						i3 = 550 - playerWidth;
					}
				}

				if (difference.getX() < 0) {
					if (clone.getX() - blastAway >= playerWidth) {
						i3 = clone.getX() - blastAway + 16;
					} else {
						i3 = 50;
					}
				}

				if (difference.getY() > 0) {
					if (clone.getY() + blastAway <= 550 - playerHeight - 16) {
						j3 = clone.getY() + blastAway;
					} else {
						j3 = 550 - playerHeight;
					}
				}

				if (difference.getY() < 0) {
					if (clone.getY() - blastAway >= 0 + playerHeight) {
						j3 = clone.getY() - blastAway;
					} else {
						j3 = 50;
					}
				}
			}
		}

		Timer reset = new Timer(15000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				blasted = false;
				skills.get(0).setVisible(true);

			}

		});
		reset.setRepeats(false);
		reset.start();

	}

	public void loseHealth(JLabel player, int playerId) {
		JLabel area = new JLabel();
		area.setBounds(player.getX(), player.getY() + 40, grassSize - 20, grassSize - 40);

		for (int i = 0; i < grass.length; i++) {
			for (int j = 0; j < grass[i].length; j++) {

				if (area.getBounds().intersects(grass[i][j].getBounds()) && grassMap[i][j] == 2 && playerId == 1 && players.contains(playerTwo)) {

					removeKittyHp.setRepeats(true);
					removeKittyHp.start();
					return;

				}

				if (area.getBounds().intersects(grass[i][j].getBounds()) && grassMap[i][j] == 3 && playerId == 2 && players.contains(playerOne)) {

					removeKillerHp.setRepeats(true);
					removeKillerHp.start();
					return;
				}
			}
		}
	}

	public void setMenu() {
		menu.setBounds(frameX * 2, frameWidth - frameY, 400, 50);
		menu.setIcon(new ImageIcon(getClass().getClassLoader().getResource("menu.png")));
		layer.add(menu, JLayeredPane.PALETTE_LAYER);

		JLabel bg = new JLabel();

		Color kittybg = new Color(241, 184, 213);
		Color killerbg = new Color(35, 35, 35);

		healthBarKitty.setBounds(0, 500 + frameY, 100, 50);
		healthBarKitty.setBorder(null);
		healthBarKitty.setHorizontalAlignment(0);
		healthBarKitty.setBackground(kittybg);
		healthBarKitty.setOpaque(true);
		layer.add(healthBarKitty, JLayeredPane.DRAG_LAYER);

		healthBarKiller.setBounds(450 + frameX, 500 + frameY, 100, 50);
		healthBarKiller.setBorder(null);
		healthBarKiller.setHorizontalAlignment(0);
		healthBarKiller.setBackground(killerbg);
		healthBarKiller.setEnabled(false);
		layer.add(healthBarKiller, JLayeredPane.DRAG_LAYER);

		healthBarKiller.setOpaque(true);
		bg.setBounds(healthBarKiller.getBounds());
		layer.add(bg);

	}

	public void setLoc() {
		if (setFruit) {
			if (players.contains(playerOne)) {
				if (up && right) {
					if (j >= speed + frameY - playerHeight * 2 / 3 && i + playerWidth - 6 <= 550) {
						i += speed * 2 / 3;
						j -= speed * 2 / 3;
						setIcon("d", playerOne);
						removeFruit();
					}
				} else if (up && left) {
					if (j >= speed + frameY - playerHeight * 2 / 3 && i >= frameX) {
						i -= speed * 2 / 3;
						j -= speed * 2 / 3;
						setIcon("a", playerOne);
						removeFruit();
					}
				} else if (down && right) {
					if (j + playerHeight + speed <= 550 && +playerWidth - 6 <= 550) {
						i += speed * 2 / 3;
						j += speed * 2 / 3;
						setIcon("d", playerOne);
						removeFruit();
					}
				} else if (down && left) {
					if (j + playerHeight + speed <= 550 && i >= frameX) {
						i -= speed * 2 / 3;
						j += speed * 2 / 3;
						setIcon("a", playerOne);
						removeFruit();
					}
				} else if (left) {
					if (i >= frameX) {
						i -= speed;
						setIcon("a", playerOne);
						removeFruit();
					}
				} else if (up) {
					if (j >= speed - 1 + frameY - playerHeight * 2 / 3) {
						j -= speed;
						setIcon("w", playerOne);
						removeFruit();
					}
				} else if (right) {
					if (i + playerWidth - 6 <= 550) {
						i += speed;
						setIcon("d", playerOne);
						removeFruit();
					}
				} else if (down) {
					if (j + playerHeight + speed <= 550) {
						j += speed;
						setIcon("s", playerOne);
						removeFruit();
					}
				}
			}

			if (players.contains(playerTwo)) {
				if (up2 && right2) {
					if (j2 >= speed2 + frameY - playerHeight * 2 / 3 && i2 + playerWidth - 6 <= 550) {
						i2 += speed2 * 2 / 3;
						j2 -= speed2 * 2 / 3;
						setIcon("d2", playerTwo);
					}
				} else if (up2 && left2) {
					if (j2 >= speed2 + frameY - playerHeight * 2 / 3 && i2 >= +frameX) {
						i2 -= speed2 * 2 / 3;
						j2 -= speed2 * 2 / 3;
						setIcon("a2", playerTwo);
					}
				} else if (down2 && right2) {
					if (j2 + playerHeight + speed2 <= 550 && i2 + playerWidth - 6 <= 550) {
						i2 += speed2 * 2 / 3;
						j2 += speed2 * 2 / 3;
						setIcon("d2", playerTwo);
					}
				} else if (down2 && left2) {
					if (j2 + playerHeight + speed2 <= 550 && i2 >= frameX) {
						i2 -= speed2 * 2 / 3;
						j2 += speed2 * 2 / 3;
						setIcon("a2", playerTwo);
					}
				} else if (left2) {
					if (i2 >= frameX) {
						i2 -= speed2;
						setIcon("a2", playerTwo);
					}
				} else if (up2) {
					if (j2 >= speed2 - 1 + frameY - playerHeight * 2 / 3) {
						j2 -= speed2;
						setIcon("w2", playerTwo);
					}
				} else if (right2) {
					if (i2 + playerWidth - 6 <= 550) {
						i2 += speed2;
						setIcon("d2", playerTwo);
					}
				} else if (down2) {
					if (j2 + playerHeight + speed2 <= 550) {
						j2 += speed2;
						setIcon("s2", playerTwo);
					}
				}

				if (players.contains(clone)) {
					if (up2 && right2) {
						if (j3 + playerHeight + speed2 <= 550 && i3 >= frameX) {
							i3 -= speed2 * 2 / 3;
							j3 += speed2 * 2 / 3;
							setIcon("a2", clone);
						}
					} else if (up2 && left2) {
						if (j3 + playerHeight + speed2 <= 550 && i3 + playerWidth - 6 <= 550) {
							i3 += speed2 * 2 / 3;
							j3 += speed2 * 2 / 3;
							setIcon("d2", clone);
						}
					} else if (down2 && right2) {
						if (j3 >= speed2 + frameY - playerHeight * 2 / 3 && i3 >= frameX) {
							i3 -= speed2 * 2 / 3;
							j3 -= speed2 * 2 / 3;
							setIcon("a2", clone);
						}
					} else if (down2 && left2) {
						if (j3 >= speed2 + frameY - playerHeight * 2 / 3 && i3 + playerWidth - 6 <= 550) {
							i3 += speed2 * 2 / 3;
							j3 -= speed2 * 2 / 3;
							setIcon("d2", clone);
						}
					} else if (left2) {
						if (i3 + playerWidth - 6 <= 550) {
							i3 += speed2;
							setIcon("d2", clone);
						}
					} else if (up2) {
						if (j3 + playerHeight + speed2 <= 550) {
							j3 += speed2;
							setIcon("s2", clone);
						}
					} else if (right2) {
						if (i3 >= frameX) {
							i3 -= speed2;
							setIcon("a2", clone);
						}
					} else if (down2) {
						if (j3 >= speed2 - 1 + frameY - playerHeight * 2 / 3) {
							j3 -= speed2;
							setIcon("w2", clone);
						}
					}
				}

				if (playerOne.getBounds().intersects(playerTwo.getBounds()) && j > j2) {
					layer.add(jumpscare, JLayeredPane.DRAG_LAYER);
					layer.add(playerOne, JLayeredPane.DRAG_LAYER, 0);
					layer.add(clone, JLayeredPane.DRAG_LAYER, 0);
					layer.add(playerTwo, JLayeredPane.DRAG_LAYER, 1);
				} else if (playerOne.getBounds().intersects(playerTwo.getBounds()) && j2 > j) {
					layer.add(jumpscare, JLayeredPane.DRAG_LAYER);
					layer.add(playerTwo, JLayeredPane.DRAG_LAYER, 0);
					layer.add(playerOne, JLayeredPane.DRAG_LAYER, 1);
					layer.add(clone, JLayeredPane.DRAG_LAYER, 1);
				}
			}
		}

		playerOne.setBounds(i, j, playerWidth, playerHeight);
		playerTwo.setBounds(i2, j2, playerWidth, playerHeight);

		if (players.contains(clone)) {
			clone.setBounds(i3, j3, playerWidth, playerHeight);
		}

	}

	public void setPlayers() {
		players.add(playerOne);
		players.add(playerTwo);

		layer.add(playerOne, JLayeredPane.DRAG_LAYER);
		setLoc();

		playerOne.setVisible(true);
		playerOne.setIcon(new ImageIcon(getClass().getClassLoader().getResource("down.png")));

		layer.add(playerTwo, JLayeredPane.DRAG_LAYER);
		setLoc();

		playerTwo.setVisible(true);
		playerTwo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("down2.png")));

	}

	public void refreshRate() {
		Timer fps = new Timer(8, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setLoc();
				touched();
				if (players.contains(clone)) {
					touchedClone();
				}
				healthBarKitty.setText("HP: " + hp);
				healthBarKiller.setText("HP: " + hp2);
			}
		});
		fps.setRepeats(true);
		fps.start();

		Timer refreshFloor = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				convertGrassBlack();
				convertGrassPink();
			}

		});
		refreshFloor.setRepeats(true);
		refreshFloor.start();
	}

	public void setGrass() {
		for (int i = 0; i < grass.length; i++) {
			for (int j = 0; j < grass[i].length; j++) {
				grass[i][j] = new JLabel();
				layer.add(grass[i][j], JLayeredPane.DEFAULT_LAYER);
				grass[i][j].setBounds(frameX + i * grassSize, frameY + j * grassSize, grassSize, grassSize);

				grass[i][j].setBorder(null);
				grass[i][j].setVisible(true);

				grass[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
				grassMap[i][j] = 1;
			}
		}

		for (int i = 0; i < fences.length; i++) {
			for (int j = 0; j < fences[i].length; j++) {
				fences[i][j] = new JLabel();
				layer.add(fences[i][j], JLayeredPane.POPUP_LAYER);

				if (i == 0 && j == 0) {
					fences[i][j].setBounds(i * grassSize, j * grassSize, grassSize, grassSize);
					fences[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("leftcorner.png")));
				} else if (j == 0 && i == 11) {
					fences[i][j].setBounds(i * grassSize, j * grassSize, grassSize, grassSize);
					fences[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("rightcorner.png")));
				} else if (j == 0) {
					fences[i][j].setBounds(i * grassSize, j * grassSize, grassSize, grassSize);
					fences[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("walltop.png")));
				} else if (i == 0) {
					fences[i][j].setBounds(i * grassSize, j * grassSize, grassSize, grassSize);
					fences[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("wall2.png")));
				} else if (i == 11) {
					fences[i][j].setBounds(i * grassSize, j * grassSize, grassSize, grassSize);
					fences[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("wall.png")));
				}
				fences[i][j].setBorder(null);
				fences[i][j].setVisible(true);

			}
		}
	}

	public void setApples() {

		for (int i = 0; i < appleCount; i++) {
			apples.add(new JButton());
			layer.add(apples.get(i), JLayeredPane.PALETTE_LAYER);
			apples.get(i).setBounds((int) (Math.random() * 460 + 10 + frameX), (int) (Math.random() * 460 + 10 + frameY), appleWidth, appleWidth);

			apples.get(i).setContentAreaFilled(false);
			apples.get(i).setFocusPainted(false);
			apples.get(i).setBorderPainted(false);

			apples.get(i).setIcon(new ImageIcon(getClass().getClassLoader().getResource("molla.png")));
			apples.get(i).setDisabledIcon(new ImageIcon(getClass().getClassLoader().getResource("molla.png")));

			apples.get(i).setEnabled(false);
		}
		setFruit = true;
		setLoc();
	}

	public void setOranges() {
		for (int i = 0; i < orangeCount; i++) {
			oranges.add(new JButton());
			layer.add(oranges.get(i), JLayeredPane.PALETTE_LAYER);
			oranges.get(i).setBounds((int) (Math.random() * 460 + 10 + frameX), (int) (Math.random() * 460 + 10 + frameY), orangeWidth, orangeHeight);

			oranges.get(i).setContentAreaFilled(false);
			oranges.get(i).setFocusPainted(false);
			oranges.get(i).setBorderPainted(false);

			oranges.get(i).setIcon(new ImageIcon(getClass().getClassLoader().getResource("porto.png")));
			oranges.get(i).setDisabledIcon(new ImageIcon(getClass().getClassLoader().getResource("porto.png")));

			oranges.get(i).setEnabled(false);
		}

		setFruit = true;
		setLoc();
	}

	public void setLemons() {

		for (int i = 0; i < lemonCount; i++) {

			lemons.add(new JButton());
			layer.add(lemons.get(i), JLayeredPane.PALETTE_LAYER);
			lemons.get(i).setBounds((int) (Math.random() * 460 + frameX + 10), (int) (Math.random() * 460 + frameY + 10), lemonWidth, lemonHeight);

			lemons.get(i).setContentAreaFilled(false);
			lemons.get(i).setFocusPainted(false);
			lemons.get(i).setBorderPainted(false);

			lemons.get(i).setVisible(true);
			lemons.get(i).setIcon(new ImageIcon(getClass().getClassLoader().getResource("lemon.png")));
			lemons.get(i).setDisabledIcon(new ImageIcon(getClass().getClassLoader().getResource("lemon.png")));

			lemons.get(i).setEnabled(false);
		}
		setFruit = true;
		setLoc();
	}

	public boolean collidesWith(JComponent component1, JComponent component2) {
		return component1.getBounds().intersects(component2.getBounds());
	}

	public void convertGrassBlack() { // killer
		if (players.contains(playerTwo)) {

			for (int i = 0; i < grass.length; i++) {
				for (int j = 0; j < grass[i].length; j++) {

					JButton area = new JButton();
					area.setBounds(playerTwo.getX(), playerTwo.getY() + 40, grassSize - 20, grassSize - 40);

					if (area.getBounds().intersects(grass[i][j].getBounds()) && (grassMap[i][j] == 1 || grassMap[i][j] == 1)) {

						grass[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass2.png")));
						grassMap[i][j] = 2;

					}

					if ((area.getBounds().intersects(grass[i][j].getBounds()) && grassMap[i][j] == 3) && playerTwo.isVisible()) {
						speed2 = setspeed2 - 1;
						loseHealth(playerTwo, playerTwoID);
					} else if ((area.getBounds().intersects(grass[i][j].getBounds())) && grassMap[i][j] != 3) {
						if (playerTwo.isVisible()) {
							speed2 = setspeed2;
						} else {
							speed2 = setspeed2 + 0.5;
						}
						removeKillerHp.stop();
					}

					if (players.contains(clone)) {
						JButton area2 = new JButton();
						area2.setBounds(clone.getX(), clone.getY() + 40, grassSize - 20, grassSize - 40);

						if (area2.getBounds().intersects(grass[i][j].getBounds()) && (grassMap[i][j] == 1 || grassMap[i][j] == 1)) {

							grass[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass2.png")));
							grassMap[i][j] = 2;

						}
					}
				}
			}
		}
	}

	public void convertGrassPink() { // kitty
		if (players.contains(playerOne)) {

			for (int i = 0; i < grass.length; i++) {
				for (int j = 0; j < grass[i].length; j++) {

					JButton area = new JButton();
					area.setBounds(playerOne.getX(), playerOne.getY() + 40, grassSize - 20, grassSize - 40);

					if (area.getBounds().intersects(grass[i][j].getBounds()) && grassMap[i][j] == 1) {

						grass[i][j].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass3.png")));
						grassMap[i][j] = 3;

					}

					if ((area.getBounds().intersects(grass[i][j].getBounds())) && grassMap[i][j] == 2) {
						speed = 3;
						loseHealth(playerOne, playerOneID);
					} else if ((area.getBounds().intersects(grass[i][j].getBounds())) && grassMap[i][j] != 2) {
						speed = 4;
						removeKittyHp.stop();
					}
				}
			}

		}
	}

	public void removeFruit() {
		removeApple();
		if (apples.isEmpty() && !oranges.isEmpty()) {
			contactDmg = 15;
			removeOrange();
		} else if (oranges.isEmpty() && !lemons.isEmpty()) {
			contactDmg = 25;
			removeLemon();
		}
	}

	public void invisibility() {

		usedPower = true;
		skills.get(5).setVisible(false);

		int temp = contactDmg;
		contactDmg = contactDmg + 5;

		playerTwo.setVisible(false);
		speed2 = setspeed2 + 1;

		Timer power = new Timer(5000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerTwo.setVisible(true);
				speed2 = setspeed2;
			}

		});
		power.setRepeats(false);
		power.start();

		Timer reset = new Timer(15000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				usedPower = false;
				skills.get(5).setVisible(true);
				contactDmg = temp;
			}

		});
		reset.setRepeats(false);
		reset.start();

	}

	public void explosion() {

		exploded = true;
		skills.get(1).setVisible(false);

		int explosions = 5;

		for (int i = 0; i < explosions; i++) {
			int x = (int) (Math.random() * 8) + 1;
			int y = (int) (Math.random() * 8) + 1;
			Timer power = new Timer(750, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					grass[x][y].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x + 1][y].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x - 1][y].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x][y + 1].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x][y - 1].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x - 1][y - 1].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x + 1][y + 1].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x + 1][y - 1].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));
					grass[x - 1][y + 1].setIcon(new ImageIcon(getClass().getClassLoader().getResource("grass.png")));

					grassMap[x][y] = 1;
					grassMap[x + 1][y] = 1;
					grassMap[x - 1][y] = 1;
					grassMap[x][y + 1] = 1;
					grassMap[x][y - 1] = 1;
					grassMap[x - 1][y - 1] = 1;
					grassMap[x + 1][y + 1] = 1;
					grassMap[x + 1][y - 1] = 1;
					grassMap[x - 1][y + 1] = 1;

				}

			});

			power.setRepeats(false);
			power.start();
		}

		Timer reset = new Timer(16500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exploded = false;
				skills.get(1).setVisible(true);
			}

		});
		reset.setRepeats(false);
		reset.start();

	}

	public void touched() {
		if (players.contains(playerTwo) && players.contains(playerOne)) {
			JLabel area = new JLabel();
			area.setBounds(playerOne.getX() + 10, playerOne.getY() + 20, playerWidth - 20, playerHeight - 40);

			if (area.getBounds().intersects(playerTwo.getBounds()) && !touch) {

				touch = true;

				if (hp > contactDmg) {
					hp -= contactDmg;
				} else if(hp<contactDmg || hp==contactDmg){
					hp = 0;
					removeKittyHp.stop();
					disableMovement();
					playerOne.setVisible(false);
					playerOne.setLocation(1000, 1000);
					JOptionPane.showMessageDialog(frame, "Hello Kitty has died! Murder Kitty has won!");
					layer.remove(playerOne);
					frame.remove(playerOne);
				}
				sound("stab.wav");

				Timer reset = new Timer(3000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						touch = false;
					}

				});
				reset.setRepeats(false);
				reset.start();
			}

		}

	}

	public void touchedClone() {
		if (players.contains(clone) && players.contains(playerOne)) {
			JLabel area = new JLabel();
			area.setBounds(playerOne.getX() + 10, playerOne.getY() + 20, playerWidth - 20, playerHeight - 40);

			if (area.getBounds().intersects(clone.getBounds()) && !cloneTouch) {

				cloneTouch = true;

				if (hp >= contactDmg) {
					hp -= contactDmg;
				} else {
					hp = 0;
					removeKittyHp.stop();
					disableMovement();
					playerOne.setVisible(false);
					playerOne.setLocation(1000, 1000);
					JOptionPane.showMessageDialog(frame, "Hello Kitty has died! Murder Kitty has won!");
					layer.remove(playerOne);
					frame.remove(playerOne);
				}
				sound("stab.wav");

				Timer reset = new Timer(3000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						cloneTouch = false;
					}

				});
				reset.setRepeats(false);
				reset.start();
			}

		}
	}

	public void removeApple() {
		for (int i = apples.size() - 1; i >= 0; i--) {
			if (collidesWith(playerOne, apples.get(i))) {
				apples.get(i).setVisible(false);
				frame.remove(apples.get(i));
				apples.remove(i);

			}
		}
		checkWin();
	}

	public void removeOrange() {
		for (int i = oranges.size() - 1; i >= 0; i--) {
			if (collidesWith(playerOne, oranges.get(i))) {
				oranges.get(i).setVisible(false);
				frame.remove(oranges.get(i));
				oranges.remove(i);

			}
		}
		checkWin();
	}

	public void removeLemon() {
		for (int i = lemons.size() - 1; i >= 0; i--) {
			if (collidesWith(playerOne, lemons.get(i))) {
				lemons.get(i).setVisible(false);
				frame.remove(lemons.get(i));
				lemons.remove(i);

			}
		}
		checkWin();
	}

	public void disableMovement() {
		up = false;
		down = false;
		left = false;
		right = false;
		up2 = false;
		down2 = false;
		left2 = false;
		right2 = false;
	}

	public void checkWin() {
		if (apples.isEmpty() && !doneApples) {

			skinID = 2;
			disableMovement();
			speed2 = setspeed2 + 0.3;
			setFruit = false;
			setOranges();
			setLoc();
			doneApples = true;
		} else if (oranges.isEmpty() && doneApples && !doneOranges) {

			skinID = 3;
			disableMovement();
			speed2 = setspeed2 + 0.6;
			setFruit = false;
			setLemons();
			setLoc();
			doneOranges = true;
		} else if (lemons.isEmpty() && doneOranges && !doneLemons) {

			disableMovement();

			JOptionPane.showMessageDialog(frame, "You've collected all the fruits! Hello Kitty has won!");
			playerTwo.setVisible(false);
			playerTwo.setLocation(1000, 1000);
			layer.remove(playerTwo);
			frame.remove(playerTwo);
			players.remove(playerTwo);
			doneLemons = true;
		}
	}

	public void setIcon(String side, JLabel player) {
		switch (skinID) {
		case 1:

			switch (side) {
			case "w":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("up.png")));
				break;
			case "a":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("left.png")));
				break;
			case "s":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("down.png")));
				break;
			case "d":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("right.png")));
				break;
			}
			break;

		case 2:
			switch (side) {
			case "w":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("uporange.png")));
				break;
			case "a":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("leftorange.png")));
				break;
			case "s":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("downorange.png")));
				break;
			case "d":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rightorange.png")));
				break;
			}
			break;

		case 3:
			switch (side) {
			case "w":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("uplemon.png")));
				break;
			case "a":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("leftlemon.png")));
				break;
			case "s":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("downlemon.png")));
				break;
			case "d":
				player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("rightlemon.png")));
				break;
			}
			break;
		}

		switch (side) {
		case "w2":
			player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("up2.png")));
			break;
		case "a2":
			player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("left2.png")));
			break;
		case "s2":
			player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("down2.png")));
			break;
		case "d2":
			player.setIcon(new ImageIcon(getClass().getClassLoader().getResource("right2.png")));
			break;

		}

	}

	public void actionPerformed(ActionEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 65:
			if (players.contains(playerOne)) {
				left = true;
			}
			break;
		case 87:
			if (players.contains(playerOne)) {
				up = true;
			}
			break;
		case 68:
			if (players.contains(playerOne)) {
				right = true;
			}
			break;
		case 83:
			if (players.contains(playerOne)) {
				down = true;
			}
			break;
		case 37:

			if (players.contains(playerTwo)) {
				left2 = true;
			}
			break;
		case 38:
			if (players.contains(playerTwo)) {
				up2 = true;
			}
			break;
		case 39:
			if (players.contains(playerTwo)) {
				right2 = true;
			}
			break;
		case 40:
			if (players.contains(playerTwo)) {
				down2 = true;
			}
			break;

		case 73:// (73 = i)
			if (!usedPower && players.contains(playerTwo)) {
				invisibility(); // i = invisibility for 5 seconds increased speed and immune to damage, cool
								// down 10 seconds
			}
			break;
		case 79:// (73 = i)
			if (!switched && players.contains(playerTwo) && players.contains(playerOne)) {
				swap(); // o = switch places, cool down 15 seconds
			}
			break;
		case 80:
			if (!mirroring && players.contains(playerTwo)) {
				mirror();
			}
			break;
		case 86:// (v=86)
			if (!exploded && players.contains(playerOne)) {
				explosion(); // v = 1-3 random explosions, cool down 15 seconds
			}
			break;
		case 67:// (c=67)
			if (!blasted && players.contains(playerOne) && players.contains(playerTwo)) {
				blast(); // c = blasts him 200 away, cool down 15 seconds
			}
			break;
		case 66:// (b=66)
			if (!healing && players.contains(playerOne)) {
				heal(); // b = heals 5 health per second over 10 seconds
			}
			break;
		}// i o p= 73 79 80 , c v b = 67 86 66

		switch (e.getKeyChar()) {
		case 'h':
			if (!jumpscared) {
				jumpscare.setVisible(true);
				sound("jssound.wav");
				Timer js = new Timer(1000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						jumpscare.setVisible(false);
						jumpscared = true;
					}

				});
				js.start();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 65:
			left = false;
			break;
		case 87:
			up = false;
			break;
		case 68:
			right = false;
			break;
		case 83:
			down = false;
			break;
		case 37:
			left2 = false;
			break;
		case 38:
			up2 = false;
			break;
		case 39:
			right2 = false;
			break;
		case 40:
			down2 = false;
			break;
		}
	}

	public void sound(String sound) {
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			try {
				clip.open(AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(sound)));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			clip.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
