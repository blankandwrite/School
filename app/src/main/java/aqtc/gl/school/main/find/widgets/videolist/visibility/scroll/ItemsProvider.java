package aqtc.gl.school.main.find.widgets.videolist.visibility.scroll;


import aqtc.gl.school.main.find.widgets.videolist.visibility.items.ListItem;

public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}
