$(document).ready(function () {
    $("#basket_button").on('click', function (data) {
        goTo("/basket");
    });
    $("#favorite_button").on('click', function (data) {
        goTo("/favorite");
    });
    $("#main_image").on('click', function (data) {
        goTo("/");
    })
});

function goTo(url) {
    document.location.href = url;
}

function processAuthentication(){

    /*if ($this->request->getCookie(self::ID_COOKIE_NAME, 'string') &&
        $this->request->getCookie(self::HASH_COOKIE_NAME, 'string')
) {
    $user = $this->mapper->findModelById($this->request->getCookie(self::ID_COOKIE_NAME, 'string'));

    if (is_object($user) &&
        md5($user->getLogin() . $user->getPassword() . Mvs\Registry::getInstance()->SECURITY['AUTHORIZATION_SALT'])
            === $this->request->getCookie(self::HASH_COOKIE_NAME, 'string')
) {
        $user->setVisitdate(new Mvs\Type\Datetime());
        $user->setIp($_SERVER['REMOTE_ADDR']);
        $this->mapper->saveModel($user);
        return $user;
    } else {
        $this->logout();
    }
}

    return $this->mapper->findModelById(-1);
    */

}