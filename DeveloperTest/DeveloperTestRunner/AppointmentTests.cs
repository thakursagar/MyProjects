using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DeveloperTestRunner
{
    class AppointmentTests : Tests
    {
        // July 12, 2011, 2:30 PM
        private DateTime time1 = new DateTime(2011, 7, 12, 14, 30, 0);
        // July 12, 2011, 3:00 PM
        private DateTime time2 = new DateTime(2011, 7, 12, 15, 0, 0);
        // July 12, 2011, 3:30 PM
        private DateTime time3 = new DateTime(2011, 7, 12, 15, 30, 0);
        // July 12, 2011, 4:00 PM
        private DateTime time4 = new DateTime(2011, 7, 12, 16, 0, 0);
      
        
        public void TestAppointment1()
        {
            Tester.AreEqual(Implementation.OccursDuring(
                time1, time2, time2, time4), false);
        }
        
        public void TestAppointment2()
        {
            Tester.AreEqual(Implementation.OccursDuring(
                time1, time3, time2, time4), true);
        }

        
        public void TestAppointment3()
        {
            Tester.AreEqual(Implementation.OccursDuring(
                time2, time4, time1, time3), true);
        }
        public void TestAppointment4()
        {
            Tester.AreEqual(Implementation.OccursDuring(
                time1, time2, time2, time3), false);
        }
        public void TestAppointment5()
        {
            Tester.AreEqual(Implementation.OccursDuring(
                time1, time2, time1, time2), true);
        }
    }
}
