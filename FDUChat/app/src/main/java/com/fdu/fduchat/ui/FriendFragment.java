package com.fdu.fduchat.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fdu.fduchat.R;
import com.fdu.fduchat.backend.Core;
import com.fdu.fduchat.backend.CoreProvider;
import com.fdu.fduchat.message.BusProvider;
import com.fdu.fduchat.message.GetContactsResult;
import com.fdu.fduchat.model.Contacts;
import com.fdu.fduchat.model.Group;
import com.fdu.fduchat.model.User;
import com.fdu.fduchat.utils.Constant;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.w3c.dom.Text;



public class FriendFragment extends Fragment implements View.OnClickListener{

    private Core core = CoreProvider.getCoreInstance();
    private User user;
    private Button addGroupButton;
    private LinearLayout friendContainerLayout;
    private TreeNode currentRoot = null;
    private View currentTreeView = null;
    private Contacts contacts = new Contacts();

    private void initView() {
        addGroupButton = (Button)getView().findViewById(R.id.addGroupButton);
        addGroupButton.setOnClickListener(this);
        friendContainerLayout = (LinearLayout)getView().findViewById(R.id.friendContainer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.friend_fragment, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getBus().register(this);
        user = (User)core.getCustomData().get(Constant.CUSTOM_DATA_KEY_USER);
        core.getContacts(user);
        initView();
//        contacts = (Contacts)core.getCustomData().get(Constant.CUSTOM_DATA_KEY_CONTACTS);
//        refreshTreeView();
    }

    private View genTreeView() {
        TreeNode root = TreeNode.root();
        for (Group group : contacts.contacts) {
            TreeNode groupNode = new TreeNode(group).setViewHolder(new GroupTreeNodeHolder(getActivity()));
            for (String friend : group.friends) {
                FriendTreeNodeItem item = new FriendTreeNodeItem();
                item.friendName = friend;
                item.group = group;
                TreeNode friendNode = new TreeNode(item).setViewHolder(new FriendTreeNodeHolder(getActivity()));
                groupNode.addChild(friendNode);
            }
            root.addChild(groupNode);
        }
        currentRoot = root;
        return new AndroidTreeView(getActivity(), root).getView();
    }

    private void refreshTreeView() {
        View newTreeView = genTreeView();
        if (currentTreeView != null) {
            friendContainerLayout.removeView(currentTreeView);
        }
        friendContainerLayout.addView(newTreeView);
        currentTreeView = newTreeView;
    }

    private void saveContacts() {
        core.putContacts(user, contacts);
    }

    @Subscribe
    public void getContactsHandler(GetContactsResult result) {
        contacts = result;
        Log.d(Constant.LOG_TAG, contacts.toString());
        refreshTreeView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addGroupButton:
                final EditText newGroupName = new EditText(getActivity());
                newGroupName.setHint("Input Group Name");
                new AlertDialog.Builder(getActivity()).setTitle("New Group")
                        .setView(newGroupName).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(Constant.LOG_TAG, newGroupName.getText().toString());
                                Group newGroup = new Group();
                                newGroup.group_name = newGroupName.getText().toString();
                                contacts.contacts.add(newGroup);
                                saveContacts();
                                refreshTreeView();
                            }
                        }).setNegativeButton("Cancel", null).show();
                break;
        }
    }

    class GroupTreeNodeHolder extends TreeNode.BaseNodeViewHolder<Group> {

        public GroupTreeNodeHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, final Group group) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.group_treenode, null, false);
            TextView groupNameText = (TextView)view.findViewById(R.id.groupNameText);
            Button addFriendButton = (Button)view.findViewById(R.id.moveFriendButton);
            addFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText newFriendName = new EditText(getActivity());
                    newFriendName.setHint("Input Friend Name");
                    new AlertDialog.Builder(getActivity()).setTitle("New Friend")
                            .setView(newFriendName).setPositiveButton("Save",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String name = newFriendName.getText().toString();
                                    Log.d(Constant.LOG_TAG, name);
                                    group.friends.add(name);
                                    saveContacts();
                                    refreshTreeView();
                                }
                            }).setNegativeButton("Cancel", null).show();
                }
            });
            Button deleteGroupButton = (Button)view.findViewById(R.id.deleteGroupButton);
            deleteGroupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contacts.contacts.remove(group);
                    saveContacts();
                    refreshTreeView();
                }
            });
            groupNameText.setText(group.group_name);
            return view;
        }

    }

    class FriendTreeNodeItem {
        public String friendName;
        public Group group;
    }

    class FriendTreeNodeHolder extends TreeNode.BaseNodeViewHolder<FriendTreeNodeItem> {

        public FriendTreeNodeHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, final FriendTreeNodeItem item) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.friend_treenode, null, false);
            TextView friendNameText = (TextView)view.findViewById(R.id.friendNameText);
            Button moveFriendButton = (Button)view.findViewById(R.id.moreFriendButton);
            Button deleteFriendButton = (Button)view.findViewById(R.id.deleteFriendButton);
            deleteFriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.group.friends.remove(item.friendName);
                    saveContacts();
                    refreshTreeView();
                }
            });
            friendNameText.setText(item.friendName);
            return view;
        }


    }
}