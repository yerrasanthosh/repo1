cd ~/vroozi-git/admin-ui
mvn clean package

cd ~/vroozi-git/admin-ui/target/adminui
rm -rf res
ln -s ~/vroozi-git/admin-ui/src/main/webapp/res res

cd ~/vroozi-git/admin-ui/target/adminui/WEB-INF
rm -rf views
ln -s ~/vroozi-git/admin-ui/src/main/webapp/WEB-INF/views views
