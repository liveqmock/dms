package com.org.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

import com.org.framework.common.DBFactory;
import com.org.framework.common.Menu;
import com.org.framework.common.Role;
import com.org.framework.common.User;
import com.org.framework.util.MenuComparator;
import com.org.framework.util.Pub;

public class Test1 {

	private Hashtable<String, ArrayList<Menu>> menutable;
	private Hashtable<String, Menu> menuarr;
	private static Test1 instance;
	private static final String ROOT_MENU = "ROOT_MENU_123456";

	private Test1() {
		DBFactory factory = new DBFactory();
		try {
			String sql = "";
			sql = "select name,title,parent,orderno,target,location,layersno,image,altimage,fresh,externals,title_en from eap_menu order by parent,orderno";
			String[][] list = factory.select(sql);
			if (list != null) {
				this.menutable = new Hashtable();
				this.menuarr = new Hashtable(list.length);
				for (int i = 0; i < list.length; i++) {
					Menu menu = new Menu();
					menu.setName(list[i][0]);
					menu.setTitle(list[i][1]);
					menu.setParent(list[i][2]);
					if ((list[i][3] == null)
							|| (list[i][3].trim().length() == 0)) {
						menu.setOrderNo("0");
					} else {
						menu.setOrderNo(list[i][3]);
					}
					menu.setTarget(list[i][4]);
					menu.setLocation(list[i][5]);
					menu.setLayersno(list[i][6]);
					menu.setImage(list[i][7]);

					menu.setAltImage(list[i][8]);
					menu.setFresh(list[i][9]);
					menu.setExternal(list[i][10]);
					menu.setTitleEn(list[i][11]);
					this.menuarr.put(menu.getName(), menu);
					if (this.menutable.get(menu.getParent()) != null) {
						ArrayList<Menu> mlist = (ArrayList) this.menutable
								.get(menu.getParent());
						mlist.add(menu);
					} else if (!Pub.empty(menu.getParent())) {
						ArrayList<Menu> mlist = (ArrayList) this.menutable
								.get(menu.getParent());
						if (mlist == null) {
							mlist = new ArrayList();
						}
						mlist.add(menu);
						this.menutable.put(menu.getParent(), mlist);
					} else {
						ArrayList<Menu> mlist = (ArrayList) this.menutable
								.get(getRootMenu());
						if (mlist == null) {
							mlist = new ArrayList();
						}
						mlist.add(menu);
						this.menutable.put(getRootMenu(), mlist);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			if (factory != null) {
				try {
					if (factory != null) {
						factory.getConnection().close();
						factory.setConnection(null);
					}
				} catch (SQLException localSQLException1) {
				}
				factory.setFactory(null);
				factory = null;
			}
		}
	}

	public Menu[] getMenus(String parent) {
		try {
			if (Pub.empty(parent)) {
				return (Menu[]) ((ArrayList) this.menutable.get(getRootMenu()))
						.toArray(new Menu[((ArrayList) this.menutable
								.get(getRootMenu())).size()]);
			}
			return (Menu[]) ((ArrayList) this.menutable.get(parent))
					.toArray(new Menu[((ArrayList) this.menutable.get(parent))
							.size()]);
		} catch (Exception e) {
		}
		return null;
	}

	public Menu getMenu(String name) {
		if (Pub.empty(name)) {
			return null;
		}
		return (Menu) this.menuarr.get(name);
	}

	public Menu[] getAllMenus() {
		Menu[] menus = new Menu[this.menuarr.values().size()];
		return (Menu[]) this.menuarr.values().toArray(menus);
	}

	public Menu[] getAllowedMenus(User user) {
		Role[] roles = user.getRoles();
		ArrayList<Menu> ml = null;
		if (roles != null) {
			for (int i = 0; i < roles.length; i++) {
				if (roles[i] != null) {
					Menu[] menus = roles[i].getMenus();
					if (menus != null) {
						if (ml == null) {
							ml = new ArrayList();
						}
						for (int j = 0; j < menus.length; j++) {
							if (!ml.contains(menus[j])) {
								ml.add(menus[j]);
							}
						}
					}
				}
			}
			if (ml != null) {
				Comparator<Object> comp = new MenuComparator();
				Collections.sort(ml, comp);
			}
		}
		if (ml == null) {
			return null;
		}
		return (Menu[]) ml.toArray(new Menu[ml.size()]);
	}

	public Menu[] getAllowedMenus(User user, String parent) {
		Role[] roles = user.getRoles();
		ArrayList<Menu> ml = null;
		if (roles != null) {
			for (int i = 0; i < roles.length; i++) {
				Menu[] menus = roles[i].getMenus();
				if (menus != null) {
					if (ml == null) {
						ml = new ArrayList();
					}
					for (int j = 0; j < menus.length; j++) {
						if (!ml.contains(menus[j])) {
							if ((Pub.empty(menus[j].getParent()))
									&& (Pub.empty(parent))) {
								ml.add(menus[j]);
							} else if ((!Pub.empty(parent))
									&& (parent.equals(menus[j].getParent()))) {
								ml.add(menus[j]);
							}
						}
					}
				}
			}
		}
		return (Menu[]) ml.toArray(new Menu[ml.size()]);
	}

	public Menu[] getAllowedMenusByParent(User user, String parent) {
		if ("100403".equals(user.getPersonKind())) {
			return getInstance().getMenus(parent);
		}
		return user.getAllowedMenus(parent);
	}

	public String getMenus(User users, Menu[] menus, String rootName,
			String sysLanguage) {
		try {
			if ((sysLanguage.equals("")) || (sysLanguage == null)) {
				sysLanguage = "1";
			}
			StringBuffer buffer = new StringBuffer(256);
			Menu[] children = null;
			String parentName = "";
			String layerName = "";
			String menuname = "";
			int layerNo = 0;
			for (int i = 0; i < menus.length; i++) {
				children = getAllowedMenusByParent(users, menus[i].getName());
				if (sysLanguage.equals("1")) {
					menuname = menus[i].getTitle();
				} else {
					menuname = menus[i].getTitleEn();
				}
				if ((children == null) || (children.length <= 0)) {
					if (!"".equals(parentName)) {
						if (parentName.equals(menus[i].getParent())) {
							if (menus[i].getParent().equals(layerName)) {
							}
						} else {
							while (layerNo > 0) {
								buffer.append("</ul>\n");
								buffer.append("</li>\n");
								layerNo--;
							}
							if ("1".equals(menus[i].getExternal())) {
								buffer.append("<li><a title='"
										+ menuname
										+ "' href='"
										+ "/szqdms"
										+ "/"
										+ menus[i].getLocation()
										+ "' target='navTab' fresh='false' external='true' rel='"
										+ menus[i].getName() + "'>" + menuname
										+ "</a></li>\n");
							} else {
								buffer.append("<li><a title='"
										+ menuname
										+ "' href='"
										+ "/szqdms"
										+ "/"
										+ menus[i].getLocation()
										+ "' target='navTab' fresh='false' external='false' rel='"
										+ menus[i].getName() + "'>" + menuname
										+ "</a></li>\n");
							}
							parentName = "";
							layerName = "";
							layerNo = 0;
							continue;
						}
					}
					if ("1".equals(menus[i].getExternal())) {
						if ("##".equals(menus[i].getLocation())) {
							buffer.append("<li><a title='"
									+ menuname
									+ "' href='#' fresh='false' external='false' onclick='doOpenExtWindow(this);' rel='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a></li>\n");
						} else {
							buffer.append("<li><a title='"
									+ menuname
									+ "' href='"
									+ "/szqdms"
									+ "/"
									+ menus[i].getLocation()
									+ "' target='navTab' fresh='false' external='true' rel='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a></li>\n");
						}
					} else {
						buffer.append("<li><a title='"
								+ menuname
								+ "' href='"
								+ "/szqdms"
								+ "/"
								+ menus[i].getLocation()
								+ "' target='navTab' fresh='false' external='false' rel='"
								+ menus[i].getName() + "'>" + menuname
								+ "</a></li>\n");
					}
				} else {
					if ((menus[i].getParent() != null)
							&& (rootName.equals(menus[i].getParent()))) {
						if (buffer.length() > 0) {
							buffer.append("</ul>\n");
							buffer.append("</div>\n");
						}
						buffer.append("<div class='accordionHeader'>\n");
						buffer.append("<h2><span>Folder</span>" + menuname
								+ "</h2>\n");
						buffer.append("</div>\n");
						buffer.append("<div class='accordionContent' style='display:none'>\n");
						buffer.append("<ul class='tree treeFolder'>\n");
					} else if ((menus[i].getLocation() != null)
							&& ("#".equals(menus[i].getLocation()))) {
						while (layerNo > 0) {
							buffer.append("</ul>\n");
							buffer.append("</li>\n");
							layerNo--;
						}
						parentName = "";
						layerName = "";
						layerNo = 0;

						buffer.append("<li>\n");
						if ("1".equals(menus[i].getExternal())) {
							buffer.append("<a title='"
									+ menuname
									+ "' href='#' external='true' fresh='false' rel='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a>\n");
						} else {
							buffer.append("<a title='"
									+ menuname
									+ "' href='#' target='navTab' fresh='false' external='false' rel='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a>\n");
						}
						buffer.append("<ul>\n");
						parentName = menus[i].getParent();
						layerName = menus[i].getName();
						layerNo = Integer.parseInt(menus[i].getLayersno());
					}
					buffer.append(getMenus(users, children, rootName,
							sysLanguage));
				}
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

	public String getChildMenusList(Menu[] grantMenus, Menu[] menus,
			String rootName, String sysLanguage) {
		try {
			StringBuffer buffer = new StringBuffer(256);
			Menu[] children = null;
			String parentName = "";
			String layerName = "";
			String menuname = "";
			int layerNo = 0;
			for (int i = 0; i < menus.length; i++) {
				children = getMenus(menus[i].getName());
				if (sysLanguage.equals("1")) {
					menuname = menus[i].getTitle();
				} else {
					menuname = menus[i].getTitleEn();
				}
				if(menuname.equals("信息查询")){
					System.out.println();
				}
				System.out.println( "i = " + i + " menuname = " + menuname);
				if ((children == null) || (children.length <= 0)) {
					if ((!"".equals(parentName))
							&& ((!parentName.equals(menus[i].getParent())) || (!menus[i]
									.getParent().equals(layerName)))) {
						boolean b = false;
						if (grantMenus != null) {
							for (Menu menu : grantMenus) {
								if (menu.getName().equals(menus[i].getName())) {
									b = true;
									break;
								}
							}
						}
						if (b) {
							buffer.append("<li><a checked='true' tname='menus' tvalue='"
									+ menus[i].getName()
									+ "'>"
									+ menuname
									+ "</a></li>\n");
						} else {
							buffer.append("<li><a tname='menus' tvalue='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a></li>\n");
						}
						while (layerNo > 0) {
							buffer.append("</ul>\n");
							buffer.append("</li>\n");
							layerNo--;
						}
						parentName = "";
						layerName = "";
						layerNo = 0;
					} else {
						boolean b = false;
						if (grantMenus != null) {
							for (Menu menu : grantMenus) {
								if (menu.getName().equals(menus[i].getName())) {
									b = true;
									break;
								}
							}
						}
						if (b) {
							buffer.append("<li><a checked='true' tname='menus' tvalue='"
									+ menus[i].getName()
									+ "'>"
									+ menuname
									+ "</a></li>\n");
						} else {
							buffer.append("<li><a tname='menus' tvalue='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a></li>\n");
						}
					}
				} else {
					if ((menus[i].getParent() != null)
							&& (rootName.equals(menus[i].getParent()))) {
						layerNo = Integer.parseInt(menus[i].getLayersno());
						while (layerNo > 0) {
							if (buffer.length() > 0) {
								buffer.append("</ul>\n");
								buffer.append("</li>\n");
							}
							layerNo--;
						}
						buffer.append("<li>\n");
						boolean b = false;
						if (grantMenus != null) {
							for (Menu menu : grantMenus) {
								if (menu.getName().equals(menus[i].getName())) {
									b = true;
									break;
								}
							}
						}
						if (b) {
							buffer.append("<a checked='true' tname='menus' tvalue='"
									+ menus[i].getName()
									+ "'>"
									+ menuname
									+ "</a>\n");
						} else {
							buffer.append("<a tname='menus' tvalue='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a>\n");
						}
						buffer.append("<ul>\n");
					} else if ((menus[i].getLocation() != null)
							&& ("#".equals(menus[i].getLocation()))) {
						if (!"".equals(parentName)) {
							if (parentName.equals(menus[i].getParent())) {
								if (menus[i].getParent().equals(layerName)) {
								}
							} else {
								while (layerNo > 0) {
									buffer.append("</ul>\n");
									buffer.append("</li>\n");
									layerNo--;
								}
								parentName = "";
								layerName = "";
								layerNo = 0;
							}
						}
						buffer.append("<li>\n");
						boolean b = false;
						if (grantMenus != null) {
							for (Menu menu : grantMenus) {
								if (menu.getName().equals(menus[i].getName())) {
									b = true;
									break;
								}
							}
						}
						if (b) {
							buffer.append("<a checked='true' tname='menus' tvalue='"
									+ menus[i].getName()
									+ "'>"
									+ menuname
									+ "</a>\n");
						} else {
							buffer.append("<a tname='menus' tvalue='"
									+ menus[i].getName() + "'>" + menuname
									+ "</a>\n");
						}
						buffer.append("<ul>\n");
						parentName = menus[i].getParent();
						layerName = menus[i].getName();
						layerNo = Integer.parseInt(menus[i].getLayersno());
					}
					buffer.append(getChildMenusList(grantMenus, children,
							rootName, sysLanguage));
				}
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

	public static synchronized Test1 getInstance() {
		if (instance == null) {
			instance = new Test1();
		}
		return instance;
	}

	public static String getRootMenu() {
		return "ROOT_MENU_123456";
	}
}
