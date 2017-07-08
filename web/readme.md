# PointJ/Web

##Version
- php 7.1.*
- Laravel 5.3.* ( via composer )
##環境構築
- インストール  
http://windows.php.net/downloads/releases/php-7.1.5-Win32-VC14-x64.zip  
https://getcomposer.org/Composer-Setup.exe  
下二つはandroidでインストールしたならしなくてok  
https://github.com/git-for-windows/git/releases/download/v2.13.0.windows.1/Git-2.13.0-64-bit.exe  
https://download.tortoisegit.org/tgit/2.4.0.0/TortoiseGit-2.4.0.2-64bit.msi
- コマンド実行
>git clone https://ry-na@bitbucket.org/pointj/web.git　(TortoiseGitでCloneでもok)  
cd web  
composer update  
cp .env.example .env  
touch database/local.sqlite  
php artisan key:generate  
php artisan serve --host=0.0.0.0 --port=80 か php -S 0.0.0.0:80 -t public  

- ブラウザでhttp://localhostにアクセス