let showAnswerBtn = document.getElementById('show-answer-btn');

if (showAnswerBtn) {
    showAnswerBtn.addEventListener('click', () => {
        showAnswerBtn.remove();
    });
}

let clearStudyTopicBtn = document.getElementById('clear-study-topic-btn');

if (clearStudyTopicBtn) {
    clearStudyTopicBtn.addEventListener('click', () => {
        clearStudyTopicBtn.disabled = true;
        let studyTopic = document.getElementById('study-topic');
        studyTopic.value = 'All';
        studyTopic.disabled = true;
        let studyTopicId = document.getElementById('studyTopicId');
        studyTopicId.value = null;
    });
}

let clearStudyStatusBtn = document.getElementById('clear-study-status-btn');

if (clearStudyStatusBtn) {
    clearStudyStatusBtn.addEventListener('click', () => {
        let statuses = document.getElementsByName('studyStatus');
        for (let i = 0; i < statuses.length; i++) {
            statuses[i].checked = false;
        }
    });
}