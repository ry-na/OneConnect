# PointJ/Web

## Version
- php 7.1.*
- Laravel 5.3.* ( via composer )
## 環境構築
- インストール  
http://windows.php.net/downloads/releases/php-7.1.5-Win32-VC14-x64.zip  
https://getcomposer.org/Composer-Setup.exe  
- コマンド実行(Windows)
>cd web  
composer update  
copy env.example .env  
type nul > database/local.sqlite  
php artisan key:generate  
php artisan migrate:refresh --seed  
php artisan serve --host=0.0.0.0 --port=80 か php -S 0.0.0.0:80 -t public  
- コマンド実行(Unix系)
>cd web
composer update
cp .env.example .env
touch database/local.sqlite
php artisan key:generate
php artisan migrate:refresh --seed
php artisan serve --host=0.0.0.0 --port=80 か php -S 0.0.0.0:80 -t public  

- ブラウザでhttp://localhostにアクセス
