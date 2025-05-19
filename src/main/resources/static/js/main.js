let showAnswerBtn = document.getElementById('show-answer-btn');

if (showAnswerBtn) {
    showAnswerBtn.addEventListener('click', () => {
        showAnswerBtn.remove();
    });
}