document.addEventListener('DOMContentLoaded', function () {
    const profileButton = document.getElementById('profile-button');
    const profileMenu = document.getElementById('profile-menu');

    if (profileButton && profileMenu) {
        profileButton.addEventListener('click', function (event) {
            event.stopPropagation(); // Evita que el clic se propague

            // Alternar la visibilidad del menú
            if (profileMenu.classList.contains('opacity-0')) {
                profileMenu.classList.remove('opacity-0', 'pointer-events-none', 'scale-95');
                profileMenu.classList.add('opacity-100', 'pointer-events-auto', 'scale-100');
                profileButton.setAttribute('aria-expanded', 'true');
            } else {
                profileMenu.classList.remove('opacity-100', 'pointer-events-auto', 'scale-100');
                profileMenu.classList.add('opacity-0', 'pointer-events-none', 'scale-95');
                profileButton.setAttribute('aria-expanded', 'false');
            }
        });

        // Cerrar el menú si se hace clic fuera
        document.addEventListener('click', function (event) {
            if (!profileButton.contains(event.target) && !profileMenu.contains(event.target)) {
                profileMenu.classList.remove('opacity-100', 'pointer-events-auto', 'scale-100');
                profileMenu.classList.add('opacity-0', 'pointer-events-none', 'scale-95');
                profileButton.setAttribute('aria-expanded', 'false');
            }
        });
    }
});